package com.web.dev.authentication.security.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class    LoginRequest {

    @NotEmpty
    String username;

    @NotEmpty
    String password;
}
