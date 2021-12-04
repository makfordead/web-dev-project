package com.web.dev.authentication.user.transaction.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class Transaction {
    @Id
    String id = UUID.randomUUID().toString().replace("-", "");
}
