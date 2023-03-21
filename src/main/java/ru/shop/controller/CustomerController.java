package ru.shop.controller;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entity.Customer;
import ru.shop.entity.SellHistory;
import ru.shop.exception.EmptyPageException;
import ru.shop.service.AuthService;
import ru.shop.service.CustomerService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/history")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<Set<SellHistory>> getAllHistory(@Context AuthService authService
    ) throws EmptyPageException {
        Customer customer = customerService.findByName(authService.getAuthInfo().getUsername());
        return new ResponseEntity<>(customerService.getAllCustomerBuyHistory(customer.getId()), HttpStatus.OK);
    }

    @GetMapping("/history/{page}/{size}")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity <Page<SellHistory>> getHistoryByPage(@Context AuthService authService,
                                                               @PathVariable(name = "page") int page,
                                                               @PathVariable(name = "size") int size
    ) throws EmptyPageException {
        if(size == 0){
            size = 10;
        }
        Customer customer = customerService.findByName(authService.getAuthInfo().getUsername());
        return new ResponseEntity<>(customerService.getCustomerBuyHistoryPage(customer.getId(), page, size), HttpStatus.OK);
    }


}
