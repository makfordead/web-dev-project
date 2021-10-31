package com.web.dev.authentication.user.friend.service;

import com.querydsl.core.types.Predicate;
import com.web.dev.authentication.exception.NotFoundException;
import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QUser;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.friend.constant.ConfirmRequest;
import com.web.dev.authentication.user.friend.constant.FriendshipStatus;
import com.web.dev.authentication.user.friend.dto.FriendShipUserResponseDto;
import com.web.dev.authentication.user.friend.dto.FriendshipRequestResponseDto;
import com.web.dev.authentication.user.friend.repository.Friendship;
import com.web.dev.authentication.user.friend.repository.FriendshipRepository;
import com.web.dev.authentication.user.friend.repository.QFriendship;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<FriendShipUserResponseDto> findFriend(Principal principal, String username) {
        FriendShipUserResponseDto friendShipUserResponseDto = new FriendShipUserResponseDto();
        final Optional<User> user = userRepository.findOne(QUser.user.username.eq(username));
        user.ifPresent((u) -> {
            friendShipUserResponseDto.setId(u.getId());
            friendShipUserResponseDto.setUsername(u.getUsername());
        });

        return new ResponseEntity<>(friendShipUserResponseDto, HttpStatus.OK);
    }

    public void sendFriendRequest(final Principal principal, final String friendId) {
        final User firstUser = userRepository.findOne(QUser.user.username.eq(principal.getName()))
                .orElseThrow(() -> new NotFoundException("invalid user"));

        final User secondUser = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("invalid friend user"));

        Friendship friendship = new Friendship();
        friendship.setFirstParty(firstUser);
        friendship.setSecondParty(secondUser);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    public ResponseEntity getFriendRequests(final Principal principal) {
        final Iterable<Friendship> friendships =
                friendshipRepository.findAll(QFriendship.friendship
                        .firstParty.username.eq(principal.getName()));

        final List<FriendshipRequestResponseDto> response =
                new ArrayList<>();

        friendships.forEach(f -> {
            response.add(modelMapper.map(f, FriendshipRequestResponseDto.class));
        });

        return new ResponseEntity(response, HttpStatus.OK);
    }

    public void acceptFriendRequest(final Principal principal, String friendshipId,
                                    ConfirmRequest confirmRequest) {
        final Friendship friendship = friendshipRepository
                .findOne(QFriendship.friendship.id.eq(friendshipId))
                .orElseThrow(() -> new NotFoundException("invalid friendship id"));

        friendship.setStatus(FriendshipStatus.valueOf(confirmRequest.name()));
        friendshipRepository.save(friendship);
    }

    public void rejectFriendRequest(final Principal principal, String friendshipId,
                                    ConfirmRequest confirmRequest) {
        final Friendship friendship = friendshipRepository
                .findOne(QFriendship.friendship.id.eq(friendshipId))
                .orElseThrow(() -> new NotFoundException("invalid friendship id"));

        friendship.setStatus(FriendshipStatus.valueOf(confirmRequest.name()));
        friendshipRepository.save(friendship);
    }
}
