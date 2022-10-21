package com.mall.choisinsa.dto.exception;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResult {
    private int code;
    private String message;

    public ErrorResult(ErrorType errorType) {
        this.code = errorType.getHttpStatus().value();
        this.message = errorType.getMessage();
    }
}
