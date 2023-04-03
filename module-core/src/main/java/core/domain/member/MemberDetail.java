package core.domain.member;

import core.domain.common.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.member.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MemberDetail extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private Long memberId;
    @Column
    private String birthday;
    @Enumerated(EnumType.STRING)
    @Column
    private GenderType gender;
    @Column
    private String height;
    @Column
    private String weight;
    @Column
    private Boolean isAcceptMarketing;
    @Column
    private Boolean isAuthenticateEmail;
    @Column
    private Boolean isAuthenticatePhone;
    @Column
    private String selfIntroduce;
    @Column
    private Long recommenderMemberId;
}
