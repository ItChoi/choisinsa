package com.mall.choisinsa.util.log;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {

    public static void logErrorTypeAndOccurClass(ErrorType type, Class c) {
        log.error("ERROR: type: {}, class: {}", type, c);
    }

}
