package com.web.dev.authentication.user.transaction.controller;

import com.web.dev.authentication.user.transaction.dto.TransactionListResponseDto;
import com.web.dev.authentication.user.transaction.dto.TransactionRequestDto;
import com.web.dev.authentication.user.transaction.repository.TransactionStatus;
import com.web.dev.authentication.user.transaction.service.TransactionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiate")
    public void initiateTransaction(final Principal principal,
                                    @RequestBody @Valid TransactionRequestDto req) {
        transactionService.createTransaction(principal, req);
    }

    @GetMapping
    public TransactionListResponseDto getTransactions(final Principal principal, @RequestParam final String friendshipId) {
        return transactionService.getTransactions(principal, friendshipId);
    }

    @PutMapping
    public void acceptOrRejectTransaction(final Principal principal,
                                          @RequestParam final String transactionId,
                                          @RequestParam final TransactionStatus transactionStatus) {
        transactionService.acceptOrRejectTransaction(principal, transactionId, transactionStatus);
    }

    @PutMapping("/cancel")
    public void cancelTransaction(final Principal principal, @RequestParam final String transactionId) {
        transactionService.cancelTransaction(principal, transactionId);
    }
}
