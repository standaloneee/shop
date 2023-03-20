package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Product;
import ru.shop.entity.Sale;
import ru.shop.entity.Tag;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.mapper.SaleMapper;
import ru.shop.repository.SaleRepository;

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
    public Set<Product> editProductSalesByTags(Set<Tag> tagSet, short discount){
     Set<Product> products = new HashSet<>();
        for (var item:tagSet) {
            products.addAll(productService.getAllProductsByTag(item));
        }
        products.forEach(item -> item.getSale().setDiscount(discount));
        return products;
    }
}