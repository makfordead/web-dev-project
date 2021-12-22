package com.web.dev.authentication.admin.dto;

import com.web.dev.authentication.user.complain.constant.ComplainStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplainResponseDto {
    String id;

    String title;

    String description;

    ComplainStatus complainStatus;

    String transactionId;
}
