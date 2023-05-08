package core.dto.request.member;

import com.mall.choisinsa.enumeration.SnsLoginType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class MemberSnsLinkRequestDto {
    @NotNull
    private SnsLoginType snsType;
}
