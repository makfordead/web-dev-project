package com.web.dev.authentication.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.stripe.config.StripeConfiguration;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeCreateCustomerService {
    @Autowired
    StripeConfiguration stripeConfiguration;

    public Customer createCustomer(final User user) throws StripeException {
        Stripe.apiKey = stripeConfiguration.getAPI_KEY();
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(user.getEmail())
                .setName(user.getFirstName())
                .build();

        return Customer.create(params);
    }
}
