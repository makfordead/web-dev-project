package com.web.dev.authentication.security.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    String id;

    String email;

    String accessToken;

    String tokenType;

    boolean profileCompleted;

    List<RoleResponse> roles;
}
