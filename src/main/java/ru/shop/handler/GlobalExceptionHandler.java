package ru.shop.handler;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shop.exception.CustomerNotFoundException;
import ru.shop.exception.CustomerNotFoundInProductException;
import ru.shop.exception.EmptyPageException;
import ru.shop.exception.ProductAlreadyExistsException;
import ru.shop.exception.ProductNotFoundException;
import ru.shop.exception.ProductOutOfStockException;
import ru.shop.exception.ProductWithSuchNameNotFoundException;
import ru.shop.exception.TagNotFoundException;

import javax.security.auth.login.LoginException;
import javax.security.auth.message.AuthException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Jwt токены не обрабатываются, а выбрасываются в логи

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
                    TagNotFoundException.class,
                    ProductAlreadyExistsException.class,
                    CustomerNotFoundException.class,
                    TagNotFoundException.class,
                    ProductWithSuchNameNotFoundException.class,
                    ProductOutOfStockException.class,
                    CustomerNotFoundInProductException.class,
                    EmptyPageException.class
            })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleExistingEntities(RuntimeException e) {
        return e.getMessage();
    }


}
