package core.dto.request.member;

import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.enumeration.member.LoginType;
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
    @NotBlank(groups = BasicMemberRegister.class)
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank(groups = BasicMemberRegister.class)
    private String email;
    @NotNull
    private LoginType loginType;
    @Valid
    private MemberDetailRegisterRequestDto memberDetail;

    @NotNull(groups = Oauth2MemberRegister.class)
    private Boolean isAutoApplyWithSnsInfo;
    @NotBlank(groups = Oauth2MemberRegister.class)
    private String oauthAccessToken;

    public Member toMember(String encodePassword) {
        return Member.builder()
                .loginId(this.loginId)
                .password(encodePassword)
                .email(EncryptionUtil.getEncryptByPlainText(this.email))
                .loginType(this.loginType)
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

    public interface Oauth2MemberRegister {

    }

    public interface BasicMemberRegister {

    }
}
