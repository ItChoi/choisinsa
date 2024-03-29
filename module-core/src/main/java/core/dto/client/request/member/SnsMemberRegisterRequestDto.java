package core.dto.client.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class SnsMemberRegisterRequestDto {
    @NotBlank
    private String snsId;
    @NotBlank
    private String oauth2AccessToken;
    @NotNull @Valid
    private MemberRegisterRequestDto memberInfo;
}
