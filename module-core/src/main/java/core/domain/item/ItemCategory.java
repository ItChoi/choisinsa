package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private ItemCategory parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ItemCategory> children;

    /**
     * 상품 카테고리 코드
     */
    @Column
    private String code;

    /**
     * 부모 상품 카테고리 ID
     */
    @Column
    private String name;

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
