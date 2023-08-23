package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 에디터 이미지
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemEditorImage extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemEditorContentId;

    /**
     * 상품 썸네일 이미지 경로
     */
    @Setter
    @Column
    private String fileUrl;

    /**
     * 상품 썸네일 이미지명
     */
    @Setter
    @Column
    private String filename;

}
