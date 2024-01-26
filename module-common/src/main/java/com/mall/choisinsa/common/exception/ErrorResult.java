/*
package com.mall.choisinsa.common.exception;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResult {
    private int code;
    private HttpStatus httpStatus;
    private String message;

    public ErrorResult(ErrorType errorType) {
        this.httpStatus = errorType.getHttpStatus();
        this.code = this.httpStatus.value();
        this.message = errorType.getMessage();
    }

    public ErrorResult(ErrorType errorType,
                       String customStr) {
        this.httpStatus = errorType.getHttpStatus();
        this.code = this.httpStatus.value();
        this.message = ErrorType.formatErrorMsg(errorType, customStr);
    }
}
*/
