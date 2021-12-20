package com.web.dev.authentication.stripe.exception;


public class PaymentFailedException extends RuntimeException{
    public PaymentFailedException(String message) {
        super(message);
    }
}
