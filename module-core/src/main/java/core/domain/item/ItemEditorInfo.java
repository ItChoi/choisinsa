package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 에디터 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemEditorInfo extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    /**
     * 상품 에디터 정보 제목
     */
    @Column
    private String title;

    /**
     * 메인 여부 (메인은 item_id 기준 하나만 가능)
     */
    @Column
    private Boolean isMain;
}
