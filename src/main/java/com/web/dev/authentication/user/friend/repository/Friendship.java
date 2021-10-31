package com.web.dev.authentication.user.friend.repository;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.friend.constant.FriendshipStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "friendship")
@ToString
public class Friendship {
    @Id
    String id = UUID.randomUUID().toString().replace("-", "");

    @OneToOne
    @JoinColumn
    User firstParty;

    @OneToOne
    @JoinColumn
    User secondParty;

    FriendshipStatus status;
}
