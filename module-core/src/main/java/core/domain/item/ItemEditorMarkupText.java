package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemEditorContentId", insertable = false, updatable = false)
    private ItemEditorContent itemEditorContent;

    /**
     * 마크업 텍스트
     */
    @Setter
    @Column
    private String text;
}
