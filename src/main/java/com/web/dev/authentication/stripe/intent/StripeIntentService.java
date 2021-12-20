package com.web.dev.authentication.stripe.intent;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.web.dev.authentication.stripe.config.StripeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StripeIntentService {
    @Autowired
    StripeConfiguration stripeConfiguration;

    public ResponseEntity createPaymentIntent() throws StripeException {
        Stripe.apiKey = stripeConfiguration.getAPI_KEY();

        return null;
    }
}