package core.dto.client.request.member;

import com.mall.choisinsa.enumeration.SnsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MemberSnsConnectRegisterRequestDto {
    @NotBlank
    private String snsId;
    @NotNull
    private SnsType snsType;
}
