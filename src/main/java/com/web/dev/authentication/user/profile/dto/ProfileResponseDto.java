package com.web.dev.authentication.user.profile.dto;

import lombok.Data;

@Data
public class ProfileResponseDto {
    String id;
    String username;
    String email;
    boolean profileCompleted;
    private String firstName;
    private String lastName;
    private String intro;
    private String gender;
    private String phoneNumber;
    private String habitualResidence;
}
