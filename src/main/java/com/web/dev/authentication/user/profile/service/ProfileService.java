package com.web.dev.authentication.user.profile.service;

import com.web.dev.authentication.exception.NotFoundException;
import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QUser;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.profile.dto.ProfileRequestDto;
import com.web.dev.authentication.user.profile.dto.ProfileResponseDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public void updateProfile(final Principal principal, final ProfileRequestDto req) {

        final User user =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setFirstName(req.getFirstName());
        user.setGender(req.getGender());
        user.setIntro(req.getIntro());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setLastName(req.getLastName());
        user.setHabitualResidence(req.getHabitualResidence());
        user.setProfileUrl(req.getProfileUrl());
        user.setProfileCompleted(true);
        userRepository.save(user);
    }

    public ProfileResponseDto getProfile() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return modelMapper.map(user, ProfileResponseDto.class);
    }
}
