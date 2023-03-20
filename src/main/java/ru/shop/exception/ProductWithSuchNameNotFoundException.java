package ru.shop.exception;

public class ProductWithSuchNameNotFoundException extends RuntimeException{
    public ProductWithSuchNameNotFoundException(String name) {
        super(String.format("Product with name %s not found", name));
    }
}
