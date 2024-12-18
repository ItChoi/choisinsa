package core.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import core.common.enumeration.category.CategoryAreaType;
import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryAreaType areaType;

    @Column
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Category> children;

    @Column
    private String categoryType;
    /**
     * 카테고리 코드
     */
    @Column
    private String code;

    @Column
    private String name;

    @Column
    private int depth;

    /**
     * 같은 계층 표시 순서
     */
    @Column
    private int displayOrder;
}
