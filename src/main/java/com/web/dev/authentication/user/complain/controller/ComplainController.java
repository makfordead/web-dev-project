package com.web.dev.authentication.user.complain.controller;

import com.web.dev.authentication.user.complain.dto.ComplainRequestDto;
import com.web.dev.authentication.user.complain.service.ComplainService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/complain")
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin
public class ComplainController {
    @Autowired
    ComplainService complainService;

    @PostMapping
    public void createComplainAgainstTransaction(@RequestBody final ComplainRequestDto req) {
        complainService.createComplain(req);
    }
}
