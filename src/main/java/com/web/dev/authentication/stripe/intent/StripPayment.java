package com.web.dev.authentication.stripe.intent;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.stripe.config.StripeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripPayment {
    @Autowired
    StripeConfiguration stripeConfiguration;

    public void pay(final User from, final User to, final Double amount) {

    }
}
