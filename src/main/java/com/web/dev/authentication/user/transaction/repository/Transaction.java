package com.web.dev.authentication.user.transaction.repository;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.friend.repository.Friendship;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
