package ru.shop.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(String id) {
        super(String.format("Tag with id %s not found", id));
    }
}
