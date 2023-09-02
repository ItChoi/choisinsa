package core.domain.category;

import com.mall.choisinsa.enumeration.category.CategoryType;
import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private Category parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Category> children;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType categoryType;

    /**
     * 같은 계층 표시 순서
     */
    @Column
    private int displayOrder;
}
