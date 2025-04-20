package com.own.identity_service.exception;

import com.own.identity_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException e, WebRequest request) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .timestamp(Instant.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handIllegalArgumentException(MethodArgumentNotValidException e, WebRequest request) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(1400)
                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                .timestamp(Instant.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
