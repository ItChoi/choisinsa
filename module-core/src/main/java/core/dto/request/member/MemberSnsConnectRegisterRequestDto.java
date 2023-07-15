package core.dto.request.member;

import com.mall.choisinsa.enumeration.SnsType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MemberSnsConnectRegisterRequestDto {
    @NotBlank
    private String snsId;
    @NotNull
    private SnsType snsType;
}
