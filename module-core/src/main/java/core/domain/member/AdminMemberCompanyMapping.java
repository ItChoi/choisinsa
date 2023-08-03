package core.domain.member;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 관리자 회사 매핑
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AdminMemberCompanyMapping extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 관리자 회원 ID
     */
    @Column
    private Long memberId;

    /**
     * 회사 ID
     */
    @Column
    private Long companyId;
}
