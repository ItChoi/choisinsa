package core.dto.response.member;

import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.util.security.EncryptionUtil;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import lombok.Getter;

@Getter
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

    @Getter
    public class MemberDetailResponseDto {
        private Long id;
        private String birthday;
        private GenderType gender;
        private String height;
        private String weight;
        private Boolean isAcceptMarketing;
        private Boolean isAuthenticateEmail;
        private Boolean isAuthenticatePhone;
        private String selfIntroduce;
        private Long recommenderMemberId;

        public MemberDetailResponseDto(MemberDetail memberDetail) {
            this.id = memberDetail.getId();
            this.birthday = memberDetail.getBirthday();
            this.gender = memberDetail.getGender();
            this.height = memberDetail.getHeight();
            this.weight = memberDetail.getWeight();
            this.isAcceptMarketing = memberDetail.getIsAcceptMarketing();
            this.isAuthenticateEmail = memberDetail.getIsAuthenticateEmail();
            this.isAuthenticatePhone = memberDetail.getIsAuthenticatePhone();
            this.selfIntroduce = memberDetail.getSelfIntroduce();
            this.recommenderMemberId = memberDetail.getRecommenderMemberId();
        }
    }

}
