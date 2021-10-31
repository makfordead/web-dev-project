package com.web.dev.authentication.user.friend.repository;

import com.web.dev.authentication.security.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FriendshipRepository extends JpaRepository<Friendship, String>, QuerydslPredicateExecutor<Friendship> {
}
