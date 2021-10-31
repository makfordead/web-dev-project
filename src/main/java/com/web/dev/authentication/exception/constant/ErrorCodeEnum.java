package com.web.dev.authentication.exception.constant;

import com.web.dev.authentication.exception.ErrorPrinter;
import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum implements ErrorPrinter {

    INVALID_PARAM(HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST),
    ENTITY_FOUND(HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST),
    NOTIFICATION_FAILED(HttpStatus.BAD_REQUEST);

    ErrorCodeEnum(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private final HttpStatus httpStatus;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}