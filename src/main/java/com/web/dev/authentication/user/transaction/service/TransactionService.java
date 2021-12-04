package com.web.dev.authentication.user.transaction.service;

import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QUser;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.friend.repository.Friendship;
import com.web.dev.authentication.user.friend.repository.FriendshipRepository;
import com.web.dev.authentication.user.transaction.dto.TransactionRequestDto;
import com.web.dev.authentication.user.transaction.repository.Transaction;
import com.web.dev.authentication.user.transaction.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    UserRepository userRepository;

    public void createTransaction(final Principal principal, TransactionRequestDto req) {
        final User user =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Friendship friendship = friendshipRepository.findById(req.getFriendshipId()).orElseThrow();
        if(!(friendship.getFirstParty().getEmail().equals(user.getEmail()) || friendship.getSecondParty().getEmail().equals(user.getEmail())))
            throw new RuntimeException("friendship id does not belong to given entities");

        final User secondUser = userRepository.findOne(QUser.user.email.eq(req.getRequestedUserId())).orElseThrow();
        final Transaction transaction = new Transaction();
        transaction.setFriendship(friendship);
        transaction.setReceivingUser(secondUser);
        transaction.setInitiatedBy(user);
        transaction.setAmount(req.getAmount());
        transactionRepository.save(transaction);
    }
}
