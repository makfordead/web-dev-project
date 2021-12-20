package com.web.dev.authentication.stripe.intent;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.stripe.config.StripeConfiguration;
import com.web.dev.authentication.stripe.exception.PaymentFailedException;
import com.web.dev.authentication.stripe.exception.PaymentMethodsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StripePayment {
    @Autowired
    StripeConfiguration stripeConfiguration;

    public void pay(final User from, final User to, final Double amount) {
        Stripe.apiKey = stripeConfiguration.getAPI_KEY();
        final String custId = from.getStripeCustomerId();
        PaymentMethodListParams params =
                PaymentMethodListParams.builder()
                        .setCustomer(custId)
                        .setType(PaymentMethodListParams.Type.CARD)
                        .build();
        PaymentMethodCollection paymentMethods = null;

        try {
            paymentMethods = PaymentMethod.list(params);
            if (paymentMethods.getData().size() == 0)
                throw new PaymentMethodsNotFoundException("Payment methods not found");

        } catch (StripeException | PaymentMethodsNotFoundException ex) {
            throw new PaymentMethodsNotFoundException("Payment methods not found", ex);
        }

        List<PaymentMethod> methods = paymentMethods.getData();
        boolean paymentSuccess = false;

        for (int i = 0; i < methods.size(); i++) {
            PaymentIntentCreateParams paymentIntentCreateParams =
                    PaymentIntentCreateParams.builder()
                            .setCurrency("usd")
                            .setAmount(Long.parseLong("" + Double.valueOf(amount).intValue()) * 10)
                            .setPaymentMethod(methods.get(i).getId())
                            .setCustomer(custId)
                            .setConfirm(true)
                            .setOffSession(true)
                            .build();
            try {
                PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
                log.info("Payment success : [{}]", paymentIntent);
                paymentSuccess = true;
                break;
            } catch (StripeException ex) {
                log.error("Error with attempting payment", ex);
            }
        }
        if (!paymentSuccess)
            throw new PaymentFailedException("Could not process payment");

    }
}
