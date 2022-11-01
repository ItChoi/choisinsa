package com.mall.choisinsa.dto.exception;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorTypeAdviceException extends RuntimeException {

    private ErrorResult errorResult;

    public ErrorTypeAdviceException(ErrorType errorType) {
        this.errorResult = new ErrorResult(errorType);
    }

}
