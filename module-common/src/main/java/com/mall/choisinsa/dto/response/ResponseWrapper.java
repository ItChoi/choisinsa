package com.mall.choisinsa.dto.response;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ResponseWrapper {
    private HttpStatus status;
    private String errorMsg;
    private Object data;

    public ResponseWrapper(HttpStatus status) {
        this.status = status;
    }

    public static ResponseWrapper ok() {
        return new ResponseWrapper(HttpStatus.OK);
    }

    public static ResponseWrapper ok(Object obj) {
        return new ResponseWrapper(HttpStatus.OK, null, obj);
    }


    public static ResponseWrapper error(ErrorType errorType) {
        return new ResponseWrapper(errorType.getHttpStatus(), errorType.getMessage(), null);
    }
}
