package com.web.dev.authentication.user.complain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplainRequestDto {
    String transactionId;
    String title;
    String description;
}
