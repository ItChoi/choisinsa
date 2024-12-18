package core.dto.client.request.member;

import core.common.enumeration.member.GenderType;
import core.common.enumeration.member.MemberType;
import core.common.util.EncryptionUtil;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private MemberSnsConnectRegisterRequestDto memberSnsConnectRegisterInfo;


    public Member toMember(String encodePassword) {
        return Member.builder()
                .loginId(this.loginId)
                .password(encodePassword)
                .email(EncryptionUtil.getEncryptByPlainText(this.email))
                .memberType(MemberType.MEMBER)
                .build();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class MemberDetailRegisterRequestDto {
        private String recommenderLoginId;
        @NotNull
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
