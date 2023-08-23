package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 에디터 마크업 텍스트
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemEditorMarkupText extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemEditorContentId;

    /**
     * 마크업 텍스트
     */
    @Setter
    @Column
    private String text;
}
