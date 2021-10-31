package com.web.dev.authentication.security.repository.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "roles", indexes = {@Index(name = "IDX_ROLES_NAME", columnList = "name")})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements Serializable {

    private static final long serialVersionUID = -687991492884005033L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

}
