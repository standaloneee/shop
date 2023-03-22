package ru.shop.exception;

public class AdminRoleChangingException extends RuntimeException{
    public AdminRoleChangingException() {
        super("ADMIN role cannot be unset via HttpRequest");
    }
}
