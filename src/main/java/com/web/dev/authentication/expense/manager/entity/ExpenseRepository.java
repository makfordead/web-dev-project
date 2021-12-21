package com.web.dev.authentication.expense.manager.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExpenseRepository extends JpaRepository<Expense, String>, QuerydslPredicateExecutor<Expense> {
}
