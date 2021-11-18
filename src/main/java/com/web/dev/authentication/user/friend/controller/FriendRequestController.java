package com.web.dev.authentication.user.friend.controller;

import com.web.dev.authentication.user.friend.constant.ConfirmRequest;
import com.web.dev.authentication.user.friend.dto.FriendShipUserResponseDto;
import com.web.dev.authentication.user.friend.service.FriendshipService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/friendship")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendRequestController {
    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/find/user")
    public ResponseEntity<FriendShipUserResponseDto> findFriend(Principal principal, @RequestParam final String email) {
        return friendshipService.findFriend(principal, email);
    }

    @PostMapping("/send/request")
    public void sendRequest(Principal principal, @RequestParam String friendId) {
        friendshipService.sendFriendRequest(principal,friendId);
    }

    @GetMapping("/get/requests")
    public ResponseEntity send(Principal principal) {
        return friendshipService.getFriendRequests(principal);
    }

    @PutMapping("/accept/request")
    public void accept(Principal principal, @RequestParam ConfirmRequest status,
                       @RequestParam String friendshipId) {
        friendshipService.acceptFriendRequest(principal, friendshipId, status);
    }

    @PutMapping("/reject/request")
    public void reject(Principal principal, @RequestParam ConfirmRequest status,
                       @RequestParam String friendshipId) {
        friendshipService.rejectFriendRequest(principal, friendshipId, status);
    }
}
