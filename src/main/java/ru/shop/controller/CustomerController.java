package ru.shop.controller;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entity.Customer;
import ru.shop.entity.SellHistory;
import ru.shop.exception.EmptyPageException;
import ru.shop.service.AuthService;
import ru.shop.service.CustomerService;
import ru.shop.utils.UUIDValid;

import java.util.Set;
import java.util.UUID;

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
    public ResponseEntity<Page<SellHistory>> getHistoryByPage(@Context AuthService authService,
                                                              @PathVariable(name = "page") int page,
                                                              @PathVariable(name = "size") int size
    ) throws EmptyPageException {
        if (size == 0) {
            size = 10;
        }
        Customer customer = customerService.findByName(authService.getAuthInfo().getUsername());
        return new ResponseEntity<>(customerService.getCustomerBuyHistoryPage(customer.getId(), page, size), HttpStatus.OK);
    }

    @GetMapping("/history/{customerId}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Set<SellHistory>> getAllHistoryAdmin(@PathVariable @UUIDValid String customerId
    ) throws EmptyPageException {
        Customer customer = customerService.findById(UUID.fromString(customerId));
        return new ResponseEntity<>(customerService.getAllCustomerBuyHistory(customer.getId()), HttpStatus.OK);
    }

    @GetMapping("/history/{customerId}/{page}/{size}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Page<SellHistory>> getHistoryByPageAdmin(
                                                                   @PathVariable(name = "page") int page,
                                                                   @PathVariable(name = "size") int size,
                                                                   @PathVariable @UUIDValid String customerId
    ) throws EmptyPageException {
        if (size == 0) {
            size = 10;
        }
        Customer customer = customerService.findById(UUID.fromString(customerId));
        return new ResponseEntity<>(customerService.getCustomerBuyHistoryPage(customer.getId(), page, size), HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Customer> editBalance(@PathVariable @UUIDValid String customerId, @RequestParam double balance){
        return new ResponseEntity<>(customerService.editBalance(UUID.fromString(customerId), balance), HttpStatus.OK);
    }
    @PutMapping("/{customerId}/addBalance")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Customer> addBalance(@PathVariable @UUIDValid String customerId, @RequestParam double amount){
        return new ResponseEntity<>(customerService.addBalance(UUID.fromString(customerId), amount), HttpStatus.OK);
    }
    @PutMapping("/all")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Set<Customer>> findAllCustomers(){
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @PutMapping("/{customerId}/get")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Customer> findCustomer(@PathVariable @UUIDValid String customerId){
        return new ResponseEntity<>(customerService.findCustomer(UUID.fromString(customerId)), HttpStatus.OK);
    }
    @DeleteMapping("/{customerId}")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<String> deleteCustomer(@PathVariable @UUIDValid String customerId){
        return new ResponseEntity<>(customerService.deleteCustomer(UUID.fromString(customerId)), HttpStatus.OK);
    }
    @PostMapping("/{customerId}/detain")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<Customer> detainCustomer(@PathVariable @UUIDValid String customerId){
        return new ResponseEntity<>(customerService.detainCustomer(UUID.fromString(customerId)), HttpStatus.OK);
    }




}
