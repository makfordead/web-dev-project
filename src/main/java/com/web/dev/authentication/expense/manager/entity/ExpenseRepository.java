package com.web.dev.authentication.expense.manager.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String>, QuerydslPredicateExecutor<Expense> {
    @Query("select e from Expense e where year(e.dateCreated) = ?2 and month(e.dateCreated) = ?1")
    List<Expense> getByYearAndMonth(int month, int year);
}
