package com.mall.choisinsa.dto.response;

import org.springframework.http.HttpStatus;

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


}
