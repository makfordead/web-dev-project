package com.web.dev.authentication.security.service.impl;


import com.web.dev.authentication.security.dto.SignUpRequest;
import com.web.dev.authentication.security.repository.RoleRepository;
import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QRole;
import com.web.dev.authentication.security.repository.entity.Role;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.security.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    PasswordEncoder encoder;

    RoleRepository roleRepository;

    UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(final SignUpRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setProfileCompleted(false);

        Optional<Role> role = roleRepository.findOne(QRole.role.name.eq("USER"));

        if(role.isPresent()){
            user.setRoles(Collections.singletonList(role.get()));
        }
        else {
            Role newRole = new Role();
            newRole.setName("USER");
            newRole = roleRepository.save(newRole);
            user.setRoles(Collections.singletonList(newRole));
        }
        userRepository.save(user);
    }
}
