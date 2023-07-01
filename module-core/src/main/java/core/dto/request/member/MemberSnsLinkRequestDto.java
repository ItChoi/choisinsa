package core.dto.request.member;

import com.mall.choisinsa.enumeration.SnsType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberSnsLinkRequestDto {
    @NotNull
    private SnsType snsType;
}
