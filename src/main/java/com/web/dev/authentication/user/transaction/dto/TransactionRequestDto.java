package com.web.dev.authentication.user.transaction.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private String friendshipId;
    private String requestedUserId;
    private Double amount;
}
