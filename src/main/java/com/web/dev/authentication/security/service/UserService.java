package com.web.dev.authentication.security.service;


import com.stripe.exception.StripeException;
import com.web.dev.authentication.security.dto.SignUpRequest;

public interface UserService {

    void save(final SignUpRequest request) throws StripeException;
}
