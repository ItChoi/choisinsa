package core.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SnsMemberRegisterRequestDto {

    private MemberRegisterRequestDto memberInfo;
}
