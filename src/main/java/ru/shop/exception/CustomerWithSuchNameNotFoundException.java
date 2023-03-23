package ru.shop.exception;

public class CustomerWithSuchNameNotFoundException extends RuntimeException{
    public CustomerWithSuchNameNotFoundException(String name) {
        super(String.format("Customer with name %s not found", name));
    }
}
