package com.web.dev.authentication.admin.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplainResponseList {
    List<ComplainResponseDto> complains;
}
