package com.web.dev.authentication.admin.user.control.service;

import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUserService {
    @Autowired
    UserRepository userRepository;

    public void delete(String userId) {
        userRepository.findOne(QUser.user.id.eq(userId))
                .ifPresent(userRepository::delete
                );
    }
}
