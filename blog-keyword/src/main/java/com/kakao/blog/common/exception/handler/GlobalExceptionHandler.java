package com.kakao.blog.common.exception.handler;


import static com.kakao.blog.common.util.MessageUtil.getSingleLineStackTrace;

import com.kakao.blog.common.exception.ForbiddenRequestException;
import com.kakao.blog.common.response.ErrorResponse;
import com.kakao.blog.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String ERROR_LOG_TEMPLATE = "error: {}, stacktrace: {}";

    @ExceptionHandler(ForbiddenRequestException.class)
    ResponseEntity<ErrorResponse<String>> handleForbiddenRequestException(Exception e) {
        ErrorResponse<String> errorResponse = ErrorResponse.of(ResponseCode.FORBIDDEN_REQUEST, e.getMessage());
        log.error(ERROR_LOG_TEMPLATE, errorResponse.getMessage(), getSingleLineStackTrace(e));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse<String>> handleAllException(Exception e) {
        ErrorResponse<String> errorResponse = ErrorResponse.of(ResponseCode.BAD_REQUEST, e.getMessage());
        log.error(ERROR_LOG_TEMPLATE, errorResponse.getMessage(), getSingleLineStackTrace(e));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
