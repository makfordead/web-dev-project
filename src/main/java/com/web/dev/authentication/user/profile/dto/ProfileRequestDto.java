package com.web.dev.authentication.user.profile.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileRequestDto {
    String firstName;
    String gender;
    String intro;
    String phoneNumber;
    String lastName;
    String habitualResidence;
}
