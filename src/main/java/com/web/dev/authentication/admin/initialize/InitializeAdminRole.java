package com.web.dev.authentication.admin.initialize;

import com.web.dev.authentication.security.repository.RoleRepository;
import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QRole;
import com.web.dev.authentication.security.repository.entity.QUser;
import com.web.dev.authentication.security.repository.entity.Role;
import com.web.dev.authentication.security.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class InitializeAdminRole implements ApplicationRunner {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("saving admin");
        Optional<User> user = userRepository.findOne(QUser.user.email.eq("admin@gmail.com"));
        user.ifPresentOrElse(c -> {
                },
                () -> {
                    User userToSave = new User();
                    userToSave.setEmail("admin@gmail.com");
                    userToSave.setPassword(encoder.encode("admin"));
                    userToSave.setProfileCompleted(false);
                    userToSave.setProfileUrl("demo-url");

                    Optional<Role> role = roleRepository.findOne(QRole.role.name.eq("ADMIN"));

                    if (role.isPresent()) {
                        userToSave.setRoles(Collections.singletonList(role.get()));
                    } else {
                        Role newRole = new Role();
                        newRole.setName("ADMIN");
                        newRole = roleRepository.save(newRole);
                        userToSave.setRoles(Collections.singletonList(newRole));
                    }
                    userRepository.save(userToSave);
                });
    }
}
