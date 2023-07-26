package core.domain.country;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 국가
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Country extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 국가명
     */
    @Column
    private String name;

    /**
     * 국가 표준 코드
     */
    @Column
    private String code;

}
