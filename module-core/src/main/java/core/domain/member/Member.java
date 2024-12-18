package core.domain.member;

import core.common.enumeration.member.MemberType;
import core.domain.common.BaseDateTimeEntity;
import core.common.enumeration.member.MemberStatus;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String loginId;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberStatus status;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    private String phoneNumber;

    @Column
    private String profileFileUrl;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberType memberType;
}


