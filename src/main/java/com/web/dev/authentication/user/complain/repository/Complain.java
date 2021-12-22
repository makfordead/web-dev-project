package com.web.dev.authentication.user.complain.repository;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.transaction.repository.Transaction;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Complain {
    @Id
    String id = UUID.randomUUID().toString().replace("-", "");

    @OneToOne
    Transaction transaction;

    String title;

    String description;

    @OneToOne
    User user;
}
