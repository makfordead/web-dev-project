package com.web.dev.authentication.user.friend.service;

import com.querydsl.core.types.ExpressionUtils;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ResponseEntity<FriendShipUserResponseDto> findFriend(Principal principal, String email) {
        FriendShipUserResponseDto friendShipUserResponseDto = new FriendShipUserResponseDto();
        final Optional<User> user = userRepository.findOne(QUser.user.email.eq(email));
        user.ifPresent((u) -> {
            friendShipUserResponseDto.setId(u.getId());
            friendShipUserResponseDto.setEmail(u.getEmail());
        });

        return new ResponseEntity<>(friendShipUserResponseDto, HttpStatus.OK);
    }


    @Transactional
    public void sendFriendRequest(final Principal principal, final String friendId) {
        final User firstUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User secondUser = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("invalid friend user"));

        final Predicate checkIfRequestIsAlreadySent = ExpressionUtils.allOf(QFriendship.friendship.firstParty.email.eq(firstUser.getEmail())
                .and(QFriendship.friendship.secondParty.email.eq(secondUser.getEmail())));


        final Predicate checkIfRequestIsReceived = ExpressionUtils.allOf(QFriendship.friendship.firstParty.email.eq(secondUser.getEmail())
                .and(QFriendship.friendship.secondParty.email.eq(firstUser.getEmail())));

        friendshipRepository.findOne(checkIfRequestIsAlreadySent).ifPresent(
                f -> {
                    throw new RuntimeException("friend request is already sent");
                });


        friendshipRepository.findOne(checkIfRequestIsReceived).ifPresent(
                friendship -> {
                    throw new RuntimeException("friend request is already received");
                }
        );

        Friendship friendship = new Friendship();
        friendship.setFirstParty(firstUser);
        friendship.setSecondParty(secondUser);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    @Transactional
    public ResponseEntity getFriendRequests(final Principal principal) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        final Iterable<Friendship> friendships =
                friendshipRepository.findAll(QFriendship.friendship
                        .secondParty.email.eq(user.getEmail()).or(
                                QFriendship.friendship.firstParty.email.eq(user.getEmail())
                                        .and(QFriendship.friendship.status.eq(FriendshipStatus.PENDING)
                                        .or(QFriendship.friendship.status.eq(FriendshipStatus.ACCEPTED)))
                        ));

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

        if (friendship.getStatus().equals(FriendshipStatus.REJECTED))
            friendshipRepository.delete(friendship);
        else
            friendshipRepository.save(friendship);
    }

    @Transactional
    public void rejectFriendRequest(final Principal principal, String friendshipId,
                                    ConfirmRequest confirmRequest) {
        final Friendship friendship = friendshipRepository
                .findOne(QFriendship.friendship.id.eq(friendshipId))
                .orElseThrow(() -> new NotFoundException("invalid friendship id"));

        friendship.setStatus(FriendshipStatus.valueOf(confirmRequest.name()));

        if (friendship.getStatus().equals(FriendshipStatus.REJECTED))
            friendshipRepository.delete(friendship);
        else
            friendshipRepository.save(friendship);
    }


    public ResponseEntity getFriends(Principal principal) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final Predicate predicate = ExpressionUtils.allOf(QFriendship.friendship.firstParty.email.eq(user.getEmail()).or(
                QFriendship.friendship.secondParty.email.eq(user.getEmail())));

        final Iterable<Friendship> friends = friendshipRepository.findAll(
                ExpressionUtils.and(predicate, QFriendship.friendship.status.eq(FriendshipStatus.ACCEPTED))
        );

        final List<Friendship> response = new ArrayList<>();
        friends.forEach(response::add);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
