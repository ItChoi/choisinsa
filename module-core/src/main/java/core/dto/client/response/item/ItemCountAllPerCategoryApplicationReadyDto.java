package core.dto.client.response.item;

import core.domain.item.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemCountAllPerCategoryApplicationReadyDto {
    // ItemCategoryCode - str value
    private Long itemCategoryId;
    private String code;
    private String parentCode;
    private int depth;
    private int displayOrder;
    private String categoryName;
    private long itemCount; // 해당 카테고리에 포함된 아이템 개수
    private long includedLowerCategoryTotalItemCount; // 현재 카테고리와 하위 카테고리들에 포함된 아이템 개수
    private Set<ItemCountAllPerCategoryApplicationReadyDto> children;

    public ItemCountAllPerCategoryApplicationReadyDto(ItemCategory itemCategory) {
        this.itemCategoryId = itemCategory.getId();
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
