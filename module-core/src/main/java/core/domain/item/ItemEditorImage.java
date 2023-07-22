package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 에디터 이미지
 */
@Getter
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
    @Column
    private String fileUrl;

    /**
     * 상품 썸네일 이미지명
     */
    @Column
    private String filename;

}
