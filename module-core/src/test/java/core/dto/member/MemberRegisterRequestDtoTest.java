package core.dto.member;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import core.dto.request.member.MemberRegisterRequestDto;
import org.junit.jupiter.api.Test;

class MemberRegisterRequestDtoTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MemberRegisterRequestDto dto = MemberRegisterRequestDto.builder()
            .loginId("")
            .email("test@test.com")
            .build();

        Set<ConstraintViolation<MemberRegisterRequestDto>> violations = validator.validate(dto);
        for (ConstraintViolation<MemberRegisterRequestDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage()); // 빈 값일시 문제가 없고 있을 땐 문제가 있는 것
        }

    }
}