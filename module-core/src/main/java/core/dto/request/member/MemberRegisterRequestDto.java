package core.dto.request.member;

import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.util.EncryptionUtil;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class MemberRegisterRequestDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @Valid
    private MemberDetailRegisterRequestDto memberDetail;

    public Member toMember() {
        return Member.builder()
                .loginId(this.loginId)
                .password(EncryptionUtil.DecryptionUtil.decrypt(this.password))
                .email(EncryptionUtil.encrypt(this.email))
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
