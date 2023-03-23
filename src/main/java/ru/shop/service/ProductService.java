package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Customer;
import ru.shop.entity.Feedback;
import ru.shop.entity.Product;
import ru.shop.entity.SellHistory;
import ru.shop.entity.Tag;
import ru.shop.exception.CustomerNotFoundInProductException;
import ru.shop.exception.ProductAlreadyExistsException;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.exception.RefundPeriodExpiredException;
import ru.shop.repository.ProductRepository;
import ru.shop.utils.ByteSequenceGenerator;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagService tagService;
    private final FeedbackService feedbackService;

    private final CustomerService customerService;

    private final SellHistoryService sellHistoryService;

    //    @Transactional(propagation = Propagation.REQUIRED)
    public Product postProduct(Product product) {
        // пока без валидаций и т.п.
        if (productRepository.findProductByName(
                product.getName()).isEmpty()) {
            product.setId(UUID.randomUUID());
        } else {
            throw new ProductAlreadyExistsException(product.getName());
        }

        if (product.getTag().isEmpty()) {
            productRepository.save(product);
        } else {
            product.setTag(tagService.addTags(product.getTag()));
        }
        product.setStatus("APPROVED");

        return productRepository.save(product);
    }

    public Product editProduct(UUID productId, Product product) {
        // пока без валидаций и т.п.
        productRepository
                .findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));
        return productRepository.save(product);
    }


    public Product getProduct(UUID productId) {
        return productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
    }

    public void saveProducts(Set<Product> products) {
        productRepository.saveAll(products);
    }
    public Product saveProduct(Product product) {
       return productRepository.save(product);
    }

    //TODO: сделать корзину и мгновенную покупку (не то тз)
    public Product buyProduct(UUID productId, UUID customerId, boolean buy) {
        Product product = findProductById(productId);
        Customer customer = customerService.findById(customerId);
        product.decreaseQuantity();
        product.setCustomers(Set.of(customer));
        // на данном моменте полагаем что у товара всегда есть организация, иначе будет NullPointerException
        product.getOrganization().getOwner().addBalance(product.getPrice()%5); //hard code %, можно сделать static const percent
        SellHistory sellHistory = new SellHistory();
        sellHistory.setProduct(product);
        sellHistory.setCustomer(customer);
        sellHistory.setPurchase_date(LocalDate.now());
        sellHistory.setStatus("Bought");
        sellHistory.setId(UUID.nameUUIDFromBytes(ByteSequenceGenerator.StringsToByteArray(
                productId.toString(),
                customerId.toString(),
                sellHistory.getPurchase_date().toString()
                        )
                )
        );

        sellHistoryService.saveSellHistory(sellHistory);
        return productRepository.save(product);
        // На данный момент пользователю после покупки может прийти продукт с количеством 0
    }

    public Set<Product> getAllProductsByTag(Tag tag) {
        return productRepository.findProductsByTag(tag);
    }

    public Product findProductById(UUID id) {
        return productRepository.findProductById(id).orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }


    public Product feedbackProduct(UUID productId, UUID customerId, Feedback feedback, double grade) {

        Product product = findProductById(productId);

        Customer customer = customerService.findById(customerId);
        if(product.getCustomers().contains(customer)) {
            feedback.setUser(customer);
            feedback.setGrade(grade);
            feedback = feedbackService.setUUIDAndSaveFeedback(feedback, productId.toString());

            // Важно! Среднюю оценку можно будет поднять путем бесконечной отправки PostMapping
            // Вариант решения - смотреть, был ли создан такой фидбэк к товару по сгенерированному UUID и если был,
            // то не вызывать описанный ниже метод

            product.updateGrade(grade);
            product.setFeedback(Set.of(feedback));
        }else{
            throw new CustomerNotFoundInProductException();
        }
        return productRepository.save(product);
    }

    public Product refundProduct(UUID productId, UUID customerId) {
        customerService.findById(customerId); // just to throw exception
        SellHistory sellHistory = sellHistoryService.findSellHistoryByProductId(productId, customerId);
        if(!sellHistory.getPurchase_date().isAfter(LocalDate.now().plusDays(1))){
            throw new RefundPeriodExpiredException();
        }
        sellHistory.setStatus("Refunded");
        sellHistoryService.saveSellHistory(sellHistory);
        return sellHistory.getProduct();
    }

    public Set<Product> getAllProducts() {
        return productRepository.findAllByOrganization_Status("APPROVED");
    }
}
