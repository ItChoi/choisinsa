package core.domain.item;

import com.mall.choisinsa.enumeration.item.ItemType;
import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 카테고리
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemCategory extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long parentId;

    /**
     * 부모 상품 카테고리 ID
     */
    @Column
    private String name;

    /**
     * 상품 타입
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ItemType itemType;

    /**
     * 계층 레벨(1부터 시작)
     */
    @Column
    private int depth;

    /**
     * 같은 계층 표시 순서
     */
    @Column
    private int displayOrder;
}
