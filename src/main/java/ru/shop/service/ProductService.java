package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entity.Product;
import ru.shop.entity.Tag;
import ru.shop.exception.ProductAlreadyExistsException;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.exception.ProductWithSuchNameNotFoundException;
import ru.shop.repository.ProductRepository;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagService tagService;

//    @Transactional(propagation = Propagation.REQUIRED)
    public Product postProduct(Product product) {
        // пока без валидаций и т.п.
        if (productRepository.findProductByName(
                product.getName()).isEmpty()) {
            product.setId(UUID.randomUUID());
        }
        else{
            throw new ProductAlreadyExistsException(product.getName());
        }

        if (product.getTag().isEmpty()) {
            productRepository.save(product);
        } else {
            product.setTag(tagService.addTags(product.getTag()));
        }

        return productRepository.save(product);
//        return null;
    }

    public Product editProduct(UUID productId, Product product) {
        // пока без валидаций и т.п.
        productRepository
                .findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));
        return productRepository.save(product);
    }

    public Set<Product> getAllProductsByTag(Tag tag) {
        return productRepository.findProductsByTag(tag);
    }

    public Product getProduct(UUID productId) {
        return productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
    }
}
