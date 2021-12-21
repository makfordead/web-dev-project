package com.web.dev.authentication.expense.manager.entity;

import com.web.dev.authentication.security.repository.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Expense {
    @Id
    String id = UUID.randomUUID().toString().replace("-", "");
    @OneToOne
    User user;
    String name;
    String description;
    Double amount;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date dateCreated;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date lastUpdated;

    @PrePersist
    protected void preInsert() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }
}
