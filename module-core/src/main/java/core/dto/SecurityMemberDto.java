package core.dto;

import com.mall.choisinsa.enumeration.member.MemberType;
import core.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class SecurityMemberDto extends User {
    private Long memberId;
    private String loginId;
    @Setter
    private String password;
    private String nickName;
    private MemberType memberType;

    public SecurityMemberDto(Long memberId,
                             String loginId,
                             String password,
                             String nickName,
                             MemberType memberType,
                             Collection<? extends GrantedAuthority> authorities) {
        super(loginId, null, authorities);
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
        this.memberType = memberType;
    }

    public SecurityMemberDto(boolean hasPassword,
                             Member securityMember,
                             Collection<? extends GrantedAuthority> authorities) {
        super(securityMember.getLoginId(), securityMember.getPassword(), authorities);
        this.memberId = securityMember.getId();
        this.loginId = securityMember.getLoginId();
        this.password = hasPassword ? securityMember.getPassword() : null;
        this.nickName = securityMember.getNickName();
        this.memberType = securityMember.getMemberType();
    }

    public SecurityMemberDto(Long memberId,
                             String loginId,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(loginId, password, authorities);
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
    }
}
