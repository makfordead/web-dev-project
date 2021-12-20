package com.web.dev.authentication.stripe.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class StripeConfiguration {
    @Value("${stripe.secret.key}")
    String API_KEY;
}