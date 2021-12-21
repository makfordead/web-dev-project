package com.web.dev.authentication.expense.manager.service;

import com.web.dev.authentication.expense.manager.dto.ExpenseRequestDto;
import com.web.dev.authentication.expense.manager.dto.ExpenseResponseDto;
import com.web.dev.authentication.expense.manager.dto.ExpenseResponseList;
import com.web.dev.authentication.expense.manager.entity.Expense;
import com.web.dev.authentication.expense.manager.entity.ExpenseRepository;
import com.web.dev.authentication.expense.manager.entity.QExpense;
import com.web.dev.authentication.security.repository.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ExpenseRepository expenseRepository;
    public void createExpense(final ExpenseRequestDto req) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Expense expense = new Expense();
        expense.setAmount(req.getAmount());
        expense.setName(req.getName());
        expense.setAmount(req.getAmount());
        expense.setDescription(req.getDescription());
        expense.setUser(user);
        expenseRepository.save(expense);
    }

    public ExpenseResponseList getExpenses() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Iterable<Expense> expenses = expenseRepository.findAll(QExpense.expense.user.email.eq(user.getEmail()));
        final List<ExpenseResponseDto> expenseResponseDtos = new ArrayList<>();
        expenses.forEach( e -> {
            expenseResponseDtos.add(modelMapper.map(e, ExpenseResponseDto.class));
        });
        return new ExpenseResponseList(expenseResponseDtos);
    }
}
