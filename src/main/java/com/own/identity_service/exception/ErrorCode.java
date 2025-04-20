package com.own.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND(1404, "Resource not found", HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND(1404, "Email not found", HttpStatus.NOT_FOUND),
    EXISTED_EMAIL(1404, "The user's email already exists", HttpStatus.NOT_FOUND),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
