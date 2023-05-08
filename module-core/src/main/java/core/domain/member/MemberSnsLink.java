package core.domain.member;

import com.mall.choisinsa.enumeration.SnsLoginType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MemberSnsLink extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private Long memberId;
    @Column
    private SnsLoginType snsType;
}
