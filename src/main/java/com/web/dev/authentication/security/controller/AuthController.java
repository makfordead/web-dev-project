package com.web.dev.authentication.security.controller;

import com.stripe.exception.StripeException;
import com.web.dev.authentication.security.dto.JwtResponse;
import com.web.dev.authentication.security.dto.LoginRequest;
import com.web.dev.authentication.security.dto.RoleResponse;
import com.web.dev.authentication.security.dto.SignUpRequest;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.security.service.UserService;
import com.web.dev.authentication.security.utils.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    JwtUtils jwtUtils;

    UserService userService;

    AuthenticationManager authenticationManager;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.ok(JwtResponse.builder().profileCompleted(userDetails.isProfileCompleted()).id(userDetails.getId()).email(userDetails.getEmail())
                .accessToken(jwtUtils.generateJwtToken(authentication)).tokenType("Bearer")
                .roles(userDetails.getRoles().stream().map(r -> {
                    RoleResponse response = new RoleResponse();
                    response.setName(r.getName());
                    response.setId(r.getId());
                    return response;
                }).collect(Collectors.toList())).build());
    }

    @PostMapping(value = "/signup")
    public final ResponseEntity<Void> signUp(@Valid @RequestBody final SignUpRequest request) throws StripeException {
        userService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
