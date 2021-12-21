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
import java.util.Objects;

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
        expense.setCategory(req.getCategory());
        expense.setDateCreated(req.getDateCreated());
        expenseRepository.save(expense);
    }

    public ExpenseResponseList getExpenses(final Integer month, final Integer year) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Iterable<Expense> expenses = new ArrayList<>();
        if(Objects.isNull(month))
            expenses = expenseRepository.findAll(QExpense.expense.user.email.eq(user.getEmail()));
        else
            expenses = expenseRepository.getByYearAndMonth(month, year);
        final List<ExpenseResponseDto> expenseResponseDtos = new ArrayList<>();
        expenses.forEach( e -> {
            expenseResponseDtos.add(modelMapper.map(e, ExpenseResponseDto.class));
        });
        return new ExpenseResponseList(expenseResponseDtos);
    }

    public void delete(final String expenseId) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Expense expense = expenseRepository.findOne(QExpense.expense.user.email.eq(user.getEmail())
        .and(QExpense.expense.id.eq(expenseId))).orElseThrow();
        expenseRepository.delete(expense);
    }
}
