package com.web.dev.authentication.user.friend.dto;

import com.web.dev.authentication.user.friend.constant.FriendshipStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendshipRequestResponseDto {
    private String id;
    private FriendShipUserResponseDto firstParty;
    private FriendShipUserResponseDto secondParty;
    private FriendshipStatus status;
}
