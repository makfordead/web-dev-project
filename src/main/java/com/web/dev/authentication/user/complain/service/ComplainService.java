package com.web.dev.authentication.user.complain.service;

import com.web.dev.authentication.security.repository.entity.User;
import com.web.dev.authentication.user.complain.dto.ComplainRequestDto;
import com.web.dev.authentication.user.complain.repository.Complain;
import com.web.dev.authentication.user.complain.repository.ComplainRepository;
import com.web.dev.authentication.user.transaction.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplainService {
    @Autowired
    ComplainRepository complainRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public void createComplain(final ComplainRequestDto req) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final Complain complain = new Complain();
        complain.setDescription(req.getDescription());
        complain.setTitle(req.getTitle());
        complain.setTransaction(transactionRepository.findById(req.getTransactionId()).orElseThrow());
        complain.setUser(user);
        complainRepository.save(complain);
    }
}
