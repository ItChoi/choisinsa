package com.mall.choisinsa.util.domain;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.util.regex.MemberRegEx;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import org.springframework.util.StringUtils;

public class MemberUtil {

    public class MemberValidator {

        public static void validateLoginOrThrow(String loginId,
                                                String password) {

            if (!StringUtils.hasText(loginId) || !StringUtils.hasText(password)) {
                throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
            }

            if (!MemberRegEx.isAvailableLoginId(loginId)) {
                throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_DATA, "아이디");
            }

        }

        public static void validateRegisterOrThrow(String loginId,
                                                   String password,
                                                   String email) {

            validateLoginOrThrow(loginId, password);

            if (!MemberRegEx.isAvailablePassword(password)) {
                throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_DATA, "비밀번호");
            }

            if (!isAvailableEmail(email)) {
                throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_DATA, "이메일");
            }
        }

        public static boolean isAvailableEmail(String email) {
            return MemberRegEx.isAvailableEmail(email);
        }

    }
}
