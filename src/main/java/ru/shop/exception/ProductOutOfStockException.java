package ru.shop.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String name) {
        super(String.format("Product with name %s is out of stock!", name));
    }
}
