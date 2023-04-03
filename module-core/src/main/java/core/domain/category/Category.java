package core.domain.category;

import core.domain.common.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.category.CategoryType;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Category extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType type;

    @Column
    private String categoryName;
}
