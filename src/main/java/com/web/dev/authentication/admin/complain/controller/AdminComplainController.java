package com.web.dev.authentication.admin.complain.controller;

import com.web.dev.authentication.admin.complain.service.AdminComplainService;
import com.web.dev.authentication.admin.dto.ComplainResponseList;
import com.web.dev.authentication.user.complain.constant.ComplainStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/admin/complain")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminComplainController {
    @Autowired
    AdminComplainService adminComplainService;
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void acceptOrRejectComplain(@RequestParam final String transactionId,
                                       @RequestParam final ComplainStatus status) {
        adminComplainService.acceptOrRejectComplain(transactionId, status);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ComplainResponseList complainResponseList() {
        return adminComplainService.getComplains();
    }
}
