package core.domain.brand;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 브랜드
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Brand extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long companyId;

    /**
     * 브랜드명 (영문)
     */
    @Column
    private String nameEn;

    /**
     * 브랜드명 (국문)
     */
    @Column
    private String nameKo;
}
