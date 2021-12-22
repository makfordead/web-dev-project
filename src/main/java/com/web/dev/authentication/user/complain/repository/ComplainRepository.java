package com.web.dev.authentication.user.complain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ComplainRepository extends JpaRepository<Complain, String>, QuerydslPredicateExecutor<Complain> {
}
