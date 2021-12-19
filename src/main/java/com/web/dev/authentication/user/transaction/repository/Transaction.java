package com.web.dev.authentication.user.transaction.repository;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.friend.repository.Friendship;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class Transaction {
    @Id
    String id = UUID.randomUUID().toString().replace("-", "");

    @OneToOne
    @JoinColumn
    User initiatedBy;

    @OneToOne
    @JoinColumn
    Friendship friendship;

    @OneToOne
    @JoinColumn
    User receivingUser;

    Double amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
