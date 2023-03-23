package ru.shop.exception;

public class RefundPeriodExpiredException extends RuntimeException{
    public RefundPeriodExpiredException() {
        super("Refund period is expired");
    }
}
