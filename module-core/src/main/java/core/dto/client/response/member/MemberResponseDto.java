package core.dto.client.response.member;

import core.common.enumeration.member.MemberStatus;
import core.common.util.EncryptionUtil;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String loginId;
    private MemberStatus status;
    private String name;
    private String email;
    private String nickName;
    private String phoneNumber;
    private String profileFileUrl;
    private MemberDetailResponseDto memberDetail;

    public MemberResponseDto(Member member,
                             MemberDetail memberDetail) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.status = member.getStatus();
        this.name = EncryptionUtil.getDecryptByEncryptedText(member.getName());
        this.email = EncryptionUtil.getDecryptByEncryptedText(member.getEmail());
        this.nickName = member.getNickName();
        this.phoneNumber = EncryptionUtil.getDecryptByEncryptedText(member.getPhoneNumber());
        this.profileFileUrl = member.getProfileFileUrl();
        this.memberDetail = new MemberDetailResponseDto(memberDetail);
    }
}
