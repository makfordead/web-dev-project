package com.web.dev.authentication.expense.manager.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseRequestDto {
    String name;
    String description;
    Double amount;
    Date dateCreated;
}
