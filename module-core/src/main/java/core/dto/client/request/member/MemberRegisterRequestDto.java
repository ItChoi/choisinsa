package core.dto.client.request.member;

import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.enumeration.member.MemberType;
import com.mall.choisinsa.util.security.EncryptionUtil;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class MemberRegisterRequestDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotNull
    private SnsType snsType;
    @Valid
    private MemberDetailRegisterRequestDto memberDetail;

    private MemberSnsConnectRegisterRequestDto memberSnsConnectRegisterInfo;


    public Member toMember(String encodePassword) {
        return Member.builder()
                .loginId(this.loginId)
                .password(encodePassword)
                .email(EncryptionUtil.getEncryptByPlainText(this.email))
                .memberType(MemberType.MEMBER)
                .build();
    }

    @Getter
    public static class MemberDetailRegisterRequestDto {
        private String recommenderLoginId;
        private Boolean isAcceptMarketing;
        @NotNull
        private Boolean isAuthenticateEmail;
        @NotNull
        private Boolean isAuthenticatePhone;

        public MemberDetail toMemberDetail(Long memberId,
                                           Long recommenderMemberId) {
            return MemberDetail.builder()
                    .memberId(memberId)
                    .recommenderMemberId(recommenderMemberId)
                    .gender(GenderType.SECRET)
                    .isAcceptMarketing(this.isAcceptMarketing)
                    .isAuthenticateEmail(this.isAuthenticateEmail)
                    .isAuthenticatePhone(this.isAuthenticatePhone)
                    .build();
        }
    }
}