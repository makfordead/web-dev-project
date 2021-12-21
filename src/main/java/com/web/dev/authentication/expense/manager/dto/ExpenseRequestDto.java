package com.web.dev.authentication.expense.manager.dto;

import com.web.dev.authentication.user.profile.dto.ProfileResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseRequestDto {
    String id;
    ProfileResponseDto user;
    String name;
    String description;
    Double amount;
    Date dateCreated;
}
