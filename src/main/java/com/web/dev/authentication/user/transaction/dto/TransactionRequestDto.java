package com.web.dev.authentication.user.transaction.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TransactionRequestDto {
    @NotEmpty
    private String friendshipId;
    @NotEmpty
    private String requestedUserId;
    private Double amount;
}
