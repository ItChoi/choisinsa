package core.dto.client.response.item;

import com.mall.choisinsa.enumeration.item.ItemCategoryCode;
import core.domain.category.Category;
import core.domain.item.ItemCategory;
import core.dto.client.response.category.ItemCountAllPerCategoryApplicationReadyDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemCountAllPerCategoryApplicationReadyDto {
    // ItemCategoryCode - str value
    private String code;
    private String parentCode;
    private int depth;
    private int displayOrder;
    private String categoryName;
    private int itemTotalCount;
    private Set<ItemCountAllPerCategoryApplicationReadyDto> children;
    
    public ItemCountAllPerCategoryApplicationReadyDto(ItemCategory itemCategory) {
        this.code = itemCategory.getCode();
        ItemCategory parent = itemCategory.getParent();
        if (parent != null) {
            this.parentCode = parent.getCode();
        }
        this.depth = itemCategory.getDepth();
        this.displayOrder = itemCategory.getDisplayOrder();
        this.categoryName = itemCategory.getName();
        Set<ItemCategory> children = itemCategory.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            this.children = convertToDtoAndSort(children);
        }
        
    }

    private Set<ItemCountAllPerCategoryApplicationReadyDto> convertToDtoAndSort(Set<ItemCategory> itemCategories) {
        return itemCategories.stream()
                .map(ItemCountAllPerCategoryApplicationReadyDto::new)
                .sorted(Comparator.comparingInt(ItemCountAllPerCategoryApplicationReadyDto::getDisplayOrder))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
