package com.mall.choisinsa.common.exception;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorTypeAdviceException extends RuntimeException {

    private ErrorResult errorResult;

    public ErrorTypeAdviceException(ErrorType errorType) {
        this.errorResult = new ErrorResult(errorType);
    }

    public ErrorTypeAdviceException(ErrorType errorType,
                                    String customStr) {
        this.errorResult = new ErrorResult(errorType, customStr);
    }

}
