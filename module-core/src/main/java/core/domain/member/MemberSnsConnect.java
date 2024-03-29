package core.domain.member;

import com.mall.choisinsa.enumeration.SnsType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MemberSnsConnect extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long memberId;

    @Column
    private String snsId;

    @Enumerated(EnumType.STRING)
    @Column
    private SnsType snsType;
}
