package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 이미지 썸네일
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemThumbnail extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemImageId;

    /**
     * 썸네일 표시 순서
     */
    @Column
    private int displayOrder;

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
