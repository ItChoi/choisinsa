package core.dto.client.request.member;

import core.common.enumeration.member.MemberType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class MemberLoginRequestDto {
    @NotNull
    private MemberType memberType;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
