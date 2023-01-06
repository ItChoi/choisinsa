package com.mall.choisinsa.web.exception.advice;

import com.mall.choisinsa.common.exception.ErrorResult;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        /*log.error("[EXCEPTION] {}:", e);*/
        return new ErrorResult(ErrorType.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        /*log.error("[EXCEPTION] {}:", e);*/
        ErrorType badRequest = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResult(badRequest), badRequest.getHttpStatus());
    }

    @ExceptionHandler(ErrorTypeAdviceException.class)
    public ResponseEntity<ErrorResult> errorTypeExHandler(ErrorTypeAdviceException e) {
        log.error("[EXCEPTION] {}:", e);
        ErrorResult errorResult = e.getErrorResult();
        return new ResponseEntity<>(errorResult, errorResult.getHttpStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResult> errorTypeExHandler(AuthenticationException e) {
        log.error("[EXCEPTION] {}:", e);
        ErrorResult errorResult = new ErrorResult(ErrorType.BAD_REQUEST);

        return new ResponseEntity<>(errorResult, errorResult.getHttpStatus());
    }
}
