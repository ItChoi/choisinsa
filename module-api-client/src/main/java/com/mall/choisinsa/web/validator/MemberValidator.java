package com.mall.choisinsa.web.validator;

import com.mall.choisinsa.domain.member.Member;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 검증 테스트 클래스 (Validation)
 */
@Component
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target,
                         Errors errors) {
        Member member = (Member) target;
        // Errors -> BingResults의 부모 클래스


    }
}
