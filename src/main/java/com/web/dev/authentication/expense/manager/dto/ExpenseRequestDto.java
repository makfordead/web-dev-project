package com.web.dev.authentication.expense.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseRequestDto {
    String name;
    String description;
    Double amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date dateCreated;
    String category;
}
