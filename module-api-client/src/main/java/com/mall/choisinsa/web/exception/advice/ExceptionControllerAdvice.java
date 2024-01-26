package com.mall.choisinsa.web.exception.advice;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.util.EnumUtils;

import java.beans.PropertyEditorSupport;


@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @InitBinder
    private void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(SnsType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(EnumUtils.findEnumInsensitiveCase(SnsType.class, text));
            }
        });
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseWrapper illegalExHandler(IllegalArgumentException e) {
        log.error("[EXCEPTION] {}:", e);
        return ResponseWrapper.error(ErrorType.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ResponseWrapper userExHandler(UserException e) {
        log.error("[EXCEPTION] {}:", e);
        return ResponseWrapper.error(ErrorType.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ErrorTypeAdviceException.class)
    public ResponseWrapper errorTypeExHandler(ErrorTypeAdviceException e) {
        log.error("[EXCEPTION] {}:", e.getMessage());
        return ResponseWrapper.error(e.getResponseWrapper().getErrorType());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseWrapper errorTypeExHandler(BindException e) {
        log.error("[EXCEPTION] {}:", e);
        return ResponseWrapper.error(ErrorType.BAD_REQUEST);
    }
}
