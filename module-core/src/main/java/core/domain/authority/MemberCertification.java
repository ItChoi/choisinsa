package core.domain.authority;

import com.mall.choisinsa.enumeration.authority.MemberCertificationType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MemberCertification extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String loginId;

    @Column
    private MemberCertificationType type;

    @Column
    private String value;

    @Column
    private LocalDateTime validTime;

}
