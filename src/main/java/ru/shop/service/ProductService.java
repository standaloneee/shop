package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entity.Customer;
import ru.shop.entity.Feedback;
import ru.shop.entity.Product;
import ru.shop.entity.Tag;
import ru.shop.exception.ProductAlreadyExistsException;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.repository.ProductRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagService tagService;
    private final FeedbackService feedbackService;

    private final CustomerService customerService;

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

    //TODO: сделать корзину и мгновенную покупку
    public Product buyProduct(UUID productId, UUID customerId, boolean buy) {
        Product product = findProductById(productId);
        Customer customer = customerService.findById(customerId);
        product.decreaseQuantity();
        product.setCustomers(Set.of(customer));
        return productRepository.save(product);
        // На данный момент пользователю после покупки может прийти продукт с количеством 0
    }

    public Set<Product> getAllProductsByTag(Tag tag) {
        return productRepository.findProductsByTag(tag);
    }

    public Product findProductById(UUID id) {
        return productRepository.findProductById(id).orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }


    public Product feedbackProduct(UUID productId, UUID customerId, Feedback feedback) {

        Product product = findProductById(productId);
        Customer customer = customerService.findById(customerId);
        feedback.setUser(customer);
        feedback = feedbackService.saveFeedback(feedback, productId.toString());

        product.setFeedback(Set.of(feedback));


        return productRepository.save(product);
    }
}
