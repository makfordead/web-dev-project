package com.web.dev.authentication.stripe.exception;

public class PaymentMethodsNotFoundException extends RuntimeException{
    public PaymentMethodsNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
