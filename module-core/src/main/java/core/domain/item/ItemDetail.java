package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 상세
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ItemDetail extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    /**
     * 품목 번호
     */
    @Setter
    @Column
    private String itemNumber;

    /**
     * 제품 소재(면 100% 등)
     */
    @Setter
    @Column
    private String materialName;

    /**
     * 제조사
     */
    @Setter
    @Column
    private String manufacturer;

    /**
     * 제조국
     */
    @Setter
    @Column
    private String manufacturerCountryName;

    /**
     * 제조연월
     */
    @Setter
    @Column
    private LocalDateTime manufacturingDate;

    /**
     * 품질보증기간
     */
    @Setter
    @Column
    private String warrantyPeriod;
}
