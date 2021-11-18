package com.web.dev.authentication.exception;


import com.web.dev.authentication.exception.constant.ErrorCodeEnum;
import com.web.dev.authentication.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
public class DefaultExceptionHandler {

    private static final String EXCEPTION_OCCURRED_MSG = "An exception occurred: ";

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(final Throwable ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), Collections.emptyList()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleException(final ServiceException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), Collections.emptyList()),
                Objects.nonNull(ex.getHttpStatus()) ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final BindingResult result = ex.getBindingResult();
        List<ErrorResponse.ErrorInfo> errorInfo = new ArrayList<>();
        result.getGlobalErrors().forEach(f -> errorInfo.add(ErrorResponse.ErrorInfo.builder().domain(f.getObjectName())
                .message(f.getDefaultMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build()));
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), errorInfo), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(final String message, final List<ErrorResponse.ErrorInfo> errorsInfo) {
        return ErrorResponse.builder().code(1).message(message).errors(errorsInfo).build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(final DataIntegrityViolationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity(Map.of("errors", List.of("User already exists")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>>  notFoundException(final NotFoundException ex){
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return new ResponseEntity(Map.of("code", "6000", "error", List.of(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }
}
