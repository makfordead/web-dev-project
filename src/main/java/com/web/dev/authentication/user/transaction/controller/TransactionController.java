package com.web.dev.authentication.user.transaction.controller;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.transaction.dto.TransactionRequestDto;
import com.web.dev.authentication.user.transaction.service.TransactionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiate")
    public void initiateTransaction(final Principal principal,
                                    @RequestBody TransactionRequestDto req) {
        transactionService.createTransaction(principal, req);
    }
}
