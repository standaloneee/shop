package ru.shop.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.exception.TagNotFoundException;

import javax.security.auth.login.LoginException;
import javax.security.auth.message.AuthException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {
                    LoginException.class
            })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleRegisterException(AuthException e) {
        return e.getMessage();
    }

    @ExceptionHandler(
            value = {
                    ProductNotFoundException.class,
                    TagNotFoundException.class
            })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleRoomIsAlreadyDeletedException(RuntimeException e) {
        return e.getMessage();
    }

}
