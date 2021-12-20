package com.web.dev.authentication.stripe.intent.controller;

import com.stripe.exception.StripeException;
import com.web.dev.authentication.stripe.intent.StripeIntentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/payment")
@CrossOrigin
public class StripePaymentIntentController {
    @Autowired
    StripeIntentService setupIntentService;
    @GetMapping("/setup-intent")
    public ResponseEntity createPaymentIntent() throws StripeException {
        return setupIntentService.createPaymentIntent();
    }
}
