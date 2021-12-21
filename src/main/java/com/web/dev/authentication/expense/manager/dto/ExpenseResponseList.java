package com.web.dev.authentication.expense.manager.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ExpenseResponseList {
    List<ExpenseResponseDto> expenses;
}
