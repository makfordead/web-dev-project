package com.web.dev.authentication.user.complain.dto;

import com.web.dev.authentication.user.complain.constant.ComplainStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplainResponseDto {
    String title;

    String description;

    ComplainStatus complainStatus;
}
