package com.web.dev.authentication.security.service;


import com.web.dev.authentication.security.dto.SignUpRequest;

public interface UserService {

    void save(final SignUpRequest request);
}
