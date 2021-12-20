package com.web.dev.authentication.stripe.intent;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.SetupIntent;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.stripe.config.StripeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeIntentService {
    @Autowired
    StripeConfiguration stripeConfiguration;

    public ResponseEntity createPaymentIntent() throws StripeException {
        Stripe.apiKey = stripeConfiguration.getAPI_KEY();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> params = new HashMap<>();
        params.put("customer", user.getStripeCustomerId());
        SetupIntent setupIntent = SetupIntent.create(params);
        return new ResponseEntity(Map.of("paymentIntentSecret",setupIntent.getClientSecret()), HttpStatus.OK);
    }
}