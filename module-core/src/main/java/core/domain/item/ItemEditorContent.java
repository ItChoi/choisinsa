package core.domain.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 에디터 내용
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemEditorContent extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemEditorInfoId;

    /**
     * 에디터 내용 타입
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ItemEditorContentType type;

    /**
     * 에디터 표시 순서
     */
    @Column
    private int displayOrder;
}
