package com.web.dev.authentication.admin.complain.service;

import com.web.dev.authentication.admin.dto.ComplainResponseDto;
import com.web.dev.authentication.admin.dto.ComplainResponseList;
import com.web.dev.authentication.user.complain.constant.ComplainStatus;
import com.web.dev.authentication.user.complain.repository.Complain;
import com.web.dev.authentication.user.complain.repository.ComplainRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminComplainService {
    @Autowired
    ComplainRepository complainRepository;
    @Autowired
    ModelMapper mapper;

    public void acceptOrRejectComplain(String complainId, ComplainStatus status) {
        final Optional<Complain> complain = complainRepository.findById(complainId);
        complain.ifPresent(c -> {
            c.setComplainStatus(status);
            complainRepository.save(c);
        });
    }

    public ComplainResponseList getComplains() {
        final List<Complain> complains = complainRepository.findAll();
        final List<ComplainResponseDto> complainResponseDtos = complains.stream().map(c -> {
            final ComplainResponseDto cmp = mapper.map(c, ComplainResponseDto.class);
            cmp.setTransactionId(c.getTransaction().getId());
            return cmp;
        })
                .collect(Collectors.toList());
        final ComplainResponseList response = new ComplainResponseList();
        response.setComplains(complainResponseDtos);
        return response;
    }

    public void deleteComplain(String complainId) {
        complainRepository.findById(complainId).ifPresent(complainRepository::delete
        );
    }
}
