package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Product;
import ru.shop.entity.Sale;
import ru.shop.entity.Tag;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.mapper.SaleMapper;
import ru.shop.repository.SaleRepository;
import ru.shop.utils.ByteSequenceGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;

    public Sale postSale(Sale sale){
        // пока без валидаций и т.п.
        return SaleMapper.INSTANCE.map(sale);
    }
    public Sale editSale(UUID saleId, Sale sale){
        // пока без валидаций и т.п.
        saleRepository
                .findSaleById(saleId)
                .orElseThrow(() -> new ProductNotFoundException(saleId.toString()));
        return saleRepository.save(sale);
    }
    public Set<Product> editProductSalesByTags(Tag[] tagSet,
                                               short discount,
                                               String start_date,
                                               int expiration_time
    ){
     Set<Product> products = new HashSet<>();
        for (var item:tagSet) {
            products.addAll(productService.getAllProductsByTag(item));
        }
        for (var item:products) {
            if(item.getSale() == null){
                item.setSale(new Sale());
                LocalDate localDate = LocalDate.parse(start_date);
                item.getSale().setDiscount(discount);
                item.getSale().setStart_date(localDate);
                item.getSale().setExpiration_time(expiration_time);
                item.getSale().setId(UUID.nameUUIDFromBytes(
                        ByteSequenceGenerator.StringToByteArray(
                                String.valueOf(discount),
                                start_date,
                                String.valueOf(expiration_time)
                        )
                ));
                saleRepository.save(item.getSale());
            }
        }
        productService.saveProducts(products);
        return products;
    }
}
