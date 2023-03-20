package ru.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entity.Product;
import ru.shop.service.ProductService;
import ru.shop.utils.UUIDValid;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    @PreAuthorize("@authService.authInfo.hasRole('USER')")
    public ResponseEntity<Product> getProduct(@PathVariable @UUIDValid String productId){
        return new ResponseEntity<>(productService.getProduct(UUID.fromString(productId)), HttpStatus.OK);
    }
    @PostMapping("")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Product> postProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.postProduct(product), HttpStatus.OK);
    }

    // тут можно было бы завернуть путь в Endpoints и обеспечить быструю смену версии API
    @PutMapping("/{productId}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Product> editProduct(@PathVariable @UUIDValid String productId, @RequestBody Product product){
        return new ResponseEntity<>(productService.editProduct(UUID.fromString(productId), product), HttpStatus.OK);
    }


}
