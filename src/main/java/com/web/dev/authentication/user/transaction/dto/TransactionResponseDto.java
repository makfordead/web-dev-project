package com.web.dev.authentication.user.transaction.dto;

import com.web.dev.authentication.user.transaction.repository.TransactionStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponseDto {
    String id;
    String initiatedBy;
    String receiveBy;
    Double amount;
    LocalDateTime createdAt;
    TransactionStatus transactionStatus;
}
