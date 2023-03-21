package ru.shop.exception;

public class CustomerNotFoundInProductException extends RuntimeException{
    public CustomerNotFoundInProductException() {
        super("You can't comment on the product unless you buy it");
    }
}
