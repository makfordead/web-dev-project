package com.web.dev.authentication.user.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TransactionRepository extends JpaRepository<Transaction, String>, QuerydslPredicateExecutor<Transaction> {
}
