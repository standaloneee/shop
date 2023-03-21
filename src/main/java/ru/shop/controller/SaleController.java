package ru.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entity.Product;
import ru.shop.entity.Sale;
import ru.shop.entity.Tag;
import ru.shop.service.SaleService;
import ru.shop.utils.UUIDValid;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @PostMapping("")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Sale> postSale(@RequestBody Sale sale) {
        return new ResponseEntity<>(saleService.postSale(sale), HttpStatus.OK);
    }

    // тут можно было бы завернуть путь в Endpoints и обеспечить быструю смену версии API
    @PutMapping("/{saleId}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Sale> editSale(@PathVariable @UUIDValid String saleId, @RequestBody Sale sale) {
        return new ResponseEntity<>(saleService.editSale(UUID.fromString(saleId), sale), HttpStatus.OK);
    }


    // Насколько я понял группа товаров == список товаров для которых надо сделать скидку по тэгу
    // из метода можно вычленить применение скидки по отдельному товару
    @PostMapping("/")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Set<Product>> editProductSalesByTags(@RequestBody Tag[] tags,
                                                               @RequestParam short discount,
                                                               @RequestParam String start_date,
                                                               @RequestParam int expiration_time
                                                               ) {
        return new ResponseEntity<>(saleService.editProductSalesByTags(tags, discount, start_date, expiration_time), HttpStatus.OK);
    }


}
