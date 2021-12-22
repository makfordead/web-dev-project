package com.web.dev.authentication.user.transaction.dto;

import com.web.dev.authentication.user.complain.dto.ComplainResponseDto;
import com.web.dev.authentication.user.profile.dto.ProfileResponseDto;
import com.web.dev.authentication.user.transaction.repository.TransactionStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponseDto {
    String id;
    ProfileResponseDto initiatedBy;
    ProfileResponseDto receiveBy;
    Double amount;
    LocalDateTime createdAt;
    TransactionStatus transactionStatus;
    List<ComplainResponseDto> complains;
}
