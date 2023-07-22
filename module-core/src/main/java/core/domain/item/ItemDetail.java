package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 상세
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column
    private String itemNumber;

    /**
     * 제품 소재(면 100% 등)
     */
    @Column
    private String materialName;

    /**
     * 제조사
     */
    @Column
    private String manufacturer;

    /**
     * 제조국
     */
    @Column
    private String manufacturerCountryName;

    /**
     * 제조연월
     */
    @Column
    private LocalDateTime manufacturingDate;

    /**
     * 품질보증기간
     */
    @Column
    private String warrantyPeriod;
}
