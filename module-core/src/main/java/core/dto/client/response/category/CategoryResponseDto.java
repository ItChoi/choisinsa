package core.dto.client.response.category;

import core.common.enumeration.category.CategoryAreaType;
import core.domain.category.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CategoryResponseDto {
    private Long id;
    private CategoryAreaType areaType;
    private Long parentId;
    private Category parent;
    @Setter
    private Set<CategoryResponseDto> children;
    private String categoryType;
    private String code;
    private String name;
    private int depth;
    private int displayOrder;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.areaType = category.getAreaType();
        this.parentId = category.getParentId();
        this.parent = category.getParent();
        Set<Category> children = category.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            this.children = convertToDtoAndSort(children);
        }
        this.categoryType = category.getCategoryType();
        this.code = category.getCode();
        this.name = category.getName();
        this.depth = category.getDepth();
        this.displayOrder = category.getDisplayOrder();
    }

    private Set<CategoryResponseDto> convertToDtoAndSort(Set<Category> categories) {
        return categories.stream()
                .map(CategoryResponseDto::new)
                .sorted(Comparator.comparingInt(CategoryResponseDto::getDisplayOrder))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
