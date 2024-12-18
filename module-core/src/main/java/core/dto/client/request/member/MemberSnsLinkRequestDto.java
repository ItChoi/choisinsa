package core.dto.client.request.member;

import core.common.enumeration.SnsType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberSnsLinkRequestDto {
    @NotNull
    private SnsType snsType;
}
