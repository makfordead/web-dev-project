package com.web.dev.authentication.user.transaction.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.web.dev.authentication.security.repository.UserRepository;
import com.web.dev.authentication.security.repository.entity.QUser;
import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.stripe.intent.StripePayment;
import com.web.dev.authentication.user.friend.repository.Friendship;
import com.web.dev.authentication.user.friend.repository.FriendshipRepository;
import com.web.dev.authentication.user.profile.dto.ProfileResponseDto;
import com.web.dev.authentication.user.transaction.dto.TransactionListResponseDto;
import com.web.dev.authentication.user.transaction.dto.TransactionRequestDto;
import com.web.dev.authentication.user.transaction.dto.TransactionResponseDto;
import com.web.dev.authentication.user.transaction.repository.QTransaction;
import com.web.dev.authentication.user.transaction.repository.Transaction;
import com.web.dev.authentication.user.transaction.repository.TransactionRepository;
import com.web.dev.authentication.user.transaction.repository.TransactionStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StripePayment stripePayment;
    @Autowired
    ModelMapper modelMapper;
    public void createTransaction(final Principal principal, TransactionRequestDto req) {
        final User user =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Friendship friendship = friendshipRepository.findById(req.getFriendshipId()).orElseThrow();
        if (!(friendship.getFirstParty().getEmail().equals(user.getEmail()) || friendship.getSecondParty().getEmail().equals(user.getEmail())))
            throw new RuntimeException("friendship id does not belong to given entities");

        final User secondUser = userRepository.findOne(QUser.user.id.eq(req.getRequestedUserId())).orElseThrow();
        final Transaction transaction = new Transaction();
        transaction.setFriendship(friendship);
        transaction.setReceivingUser(secondUser);
        transaction.setInitiatedBy(user);
        transaction.setAmount(req.getAmount());
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);
    }

    public TransactionListResponseDto getTransactions(final Principal principal, final String friendshipId) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Predicate predicate = QTransaction.transaction.friendship.id.eq(friendshipId)
                .and(QTransaction.transaction.initiatedBy.email.eq(user.getEmail())
                        .or(QTransaction.transaction.receivingUser.email.eq(user.getEmail())));

        final Iterable<Transaction> transactions = transactionRepository.findAll(predicate);
        final ArrayList<TransactionResponseDto> transactionList = new ArrayList();
        transactions.forEach(t -> transactionList.add(map(t)));
        final TransactionListResponseDto response = new TransactionListResponseDto();
        response.setTransactions(transactionList);
        return response;
    }

    private TransactionResponseDto map(final Transaction transaction) {
        final TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setAmount(transaction.getAmount());
        transactionResponseDto.setCreatedAt(transaction.getCreatedAt());
        transactionResponseDto.setInitiatedBy(modelMapper.map(transaction.getInitiatedBy(), ProfileResponseDto.class));
        transactionResponseDto.setReceiveBy(modelMapper.map(transaction.getReceivingUser(), ProfileResponseDto.class));
        transactionResponseDto.setId(transaction.getId());
        transactionResponseDto.setTransactionStatus(transaction.getTransactionStatus());
        return transactionResponseDto;
    }

    public void acceptOrRejectTransaction(Principal principal, String transactionId, TransactionStatus transactionStatus) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Transaction transaction = transactionRepository.findOne(QTransaction.transaction.id.eq(transactionId).and(QTransaction.transaction.receivingUser.email.eq(user.getEmail()))).orElseThrow();
        transaction.setTransactionStatus(transactionStatus);
        if(transactionStatus.equals(TransactionStatus.ACCEPTED))
            stripePayment.pay(user, transaction.getReceivingUser(), transaction.getAmount());
        transactionRepository.save(transaction);
    }


    public void cancelTransaction(Principal principal, String transactionId) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Transaction transaction = transactionRepository.findOne(QTransaction.transaction.id.eq(transactionId).and(QTransaction.transaction.initiatedBy.email.eq(user.getEmail()))).orElseThrow();
        transaction.setTransactionStatus(TransactionStatus.CANCELLED);
        transactionRepository.save(transaction);
    }


    public TransactionListResponseDto getSelfTransactions() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Predicate predicate = QTransaction.transaction.initiatedBy.email.eq(user.getEmail()).or(QTransaction.transaction.receivingUser.email.eq(user.getEmail()));
        final Iterable<Transaction> transactions = transactionRepository.findAll(predicate);
        final ArrayList<TransactionResponseDto> transactionList = new ArrayList();
        transactions.forEach(t -> transactionList.add(map(t)));
        final TransactionListResponseDto response = new TransactionListResponseDto();
        response.setTransactions(transactionList);
        return response;
    }
}
