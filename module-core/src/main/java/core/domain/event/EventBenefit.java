package core.domain.event;

import core.domain.common.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.BenefitType;
import com.mall.choisinsa.enumeration.ConditionType;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class EventBenefit extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private Long eventId;
    @Enumerated(EnumType.STRING)
    @Column
    private ConditionType conditionType;
    @Column
    private int conditionNumber;
    @Enumerated(EnumType.STRING)
    @Column
    private BenefitType benefitType;
    @Column
    private int benefitNumber;
    @Column
    private String title;
    @Column
    private String content;
}
