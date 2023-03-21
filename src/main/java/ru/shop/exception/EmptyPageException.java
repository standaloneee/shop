package ru.shop.exception;



public class EmptyPageException extends RuntimeException{
    public EmptyPageException() {
        super("Page is empty");
    }
}
