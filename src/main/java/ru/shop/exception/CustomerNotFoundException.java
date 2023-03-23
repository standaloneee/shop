package ru.shop.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String id) {
        super(String.format("Customer with id %s not found", id));
    }
}
