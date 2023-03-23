package ru.shop.controller;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entity.Customer;
import ru.shop.entity.Feedback;
import ru.shop.entity.Product;
import ru.shop.service.AuthService;
import ru.shop.service.CustomerService;
import ru.shop.service.ProductService;
import ru.shop.utils.UUIDValid;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/{productId}")
    @PreAuthorize("@authService.authInfo.authenticated")
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

    /**
     *
     *
     * @param productId - id продукта
     * @param buy - true - купить и оформить сразу, false - положить в корзину (для будущего функционала)
     */
    @PostMapping("/{productId}")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<Product> buyProduct(@PathVariable @UUIDValid String productId,
                                              @RequestParam @UUIDValid String customerId,
                                              @RequestParam boolean buy ){
        return new ResponseEntity<>(productService.buyProduct(UUID.fromString(productId), UUID.fromString(customerId), buy), HttpStatus.OK);
    }
    @PostMapping("/feedback/{productId}")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<Product> feedbackProduct(@PathVariable @UUIDValid String productId,
                                                   @RequestParam @UUIDValid String customerId,
                                                   @RequestParam @Min(0) @Max(5) double grade,
                                                   @RequestBody Feedback feedback){
        return new ResponseEntity<>(productService.feedbackProduct(UUID.fromString(productId), UUID.fromString(customerId), feedback, grade), HttpStatus.OK);
    }

    @PostMapping("/refund/{productId}")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<Product> refundProduct(@Context AuthService authService, @PathVariable @UUIDValid String productId){
        Customer customer = customerService.findByName(authService.getAuthInfo().getUsername());
        return new ResponseEntity<>(productService.refundProduct(UUID.fromString(productId), customer.getId()), HttpStatus.OK);

    }

    @GetMapping("/all")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<Set<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);

    }
}
