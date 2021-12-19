package com.web.dev.authentication.user.transaction.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponseDto {
    String initiatedBy;
    String receiveBy;
    Double amount;
    LocalDateTime createdAt;
}
