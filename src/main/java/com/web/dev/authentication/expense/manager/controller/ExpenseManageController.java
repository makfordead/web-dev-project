package com.web.dev.authentication.expense.manager.controller;

import com.web.dev.authentication.expense.manager.dto.ExpenseRequestDto;
import com.web.dev.authentication.expense.manager.dto.ExpenseResponseList;
import com.web.dev.authentication.expense.manager.service.EntityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/expense")
@CrossOrigin
public class ExpenseManageController {
    @Autowired
    EntityService entityService;
    @PostMapping
    public void createExpense(@RequestBody final ExpenseRequestDto req) {
        entityService.createExpense(req);
    }

    @GetMapping
    public ExpenseResponseList getExpenses() {
        return entityService.getExpenses();
    }
}
