package com.web.dev.authentication.admin.user.control.controller;

import com.web.dev.authentication.admin.user.control.service.AdminUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable final String userId) {
        adminUserService.delete(userId);
    }
}
