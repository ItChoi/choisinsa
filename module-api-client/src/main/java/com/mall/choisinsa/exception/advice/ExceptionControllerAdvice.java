package com.mall.choisinsa.exception.advice;

import com.mall.choisinsa.dto.exception.ErrorResult;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[EXCEPTION] {}:", e);
        return new ErrorResult(ErrorType.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[EXCEPTION] {}:", e);
        ErrorType badRequest = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResult(badRequest), badRequest.getHttpStatus());
    }
}
