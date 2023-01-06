package com.mall.choisinsa.util;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.util.regex.MemberRegEx;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import org.springframework.util.StringUtils;

public class MemberUtil {

    public class MemberValidatorThrow {
        public static void validateMemberBy(String loginId,
                                            String password) {

            if (!StringUtils.hasText(loginId) || !StringUtils.hasText(password)) {
                throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
            }

            if (!MemberRegEx.isAvailableLoginId(loginId)) {
                throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_LOGIN_ID);
            }

            if (!MemberRegEx.isAvailablePassword(password)) {
                throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_PASSWORD);
            }
        }

    }
}
