package com.web.dev.authentication.user.profile.controller;

import com.web.dev.authentication.user.profile.dto.ProfileRequestDto;
import com.web.dev.authentication.user.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/profile")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PutMapping("/update")
    public void updateProfile(Principal principal, @RequestBody final ProfileRequestDto req) {
        profileService.updateProfile(principal, req);
    }
}
