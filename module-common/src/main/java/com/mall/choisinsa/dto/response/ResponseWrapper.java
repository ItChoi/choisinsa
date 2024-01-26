package com.mall.choisinsa.dto.response;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.mall.choisinsa.enumeration.exception.ErrorType.*;

@Getter
@AllArgsConstructor
public class ResponseWrapper {
    private HttpStatus status;
    private ErrorType errorType;
    private String errorMsg;
    private Object data;

    public ResponseWrapper(HttpStatus status) {
        this.status = status;
    }

    public static ResponseWrapper ok() {
        return new ResponseWrapper(HttpStatus.OK);
    }

    public static ResponseWrapper ok(Object obj) {
        return new ResponseWrapper(HttpStatus.OK, null, null, obj);
    }


    public static ResponseWrapper error(ErrorType errorType) {
        return new ResponseWrapper(errorType.getHttpStatus(), errorType, errorType.getMessage(), null);
    }

    public static ResponseWrapper error(ErrorType errorType,
                                        String customStr) {
        return new ResponseWrapper(errorType.getHttpStatus(), errorType, formatErrorMsg(errorType, customStr), null);
    }
}
