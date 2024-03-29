package core.common.exception;

import core.dto.ResponseWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorTypeAdviceException extends RuntimeException {

    private ResponseWrapper responseWrapper;

    public ErrorTypeAdviceException(ErrorType errorType) {
        this.responseWrapper = ResponseWrapper.error(errorType);
    }

    public ErrorTypeAdviceException(ErrorType errorType,
                                    String customStr) {
        this.responseWrapper = ResponseWrapper.error(errorType, customStr);
    }

}
