package core.dto.client.response.category;

import core.common.enumeration.category.CategoryType;
import lombok.Getter;

@Getter
public class CategoryTypeResponseDto {
    private String name;
    private String categoryType;
    private String code;

    public CategoryTypeResponseDto(CategoryType categoryType) {
        this.name = categoryType.getCategoryName();
        this.categoryType = categoryType.name();
        this.code = categoryType.getCode();
    }

}
