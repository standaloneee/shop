package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Product;
import ru.shop.repository.ProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product postProduct(Product product){
        // пока без валидаций и т.п.
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }
}
