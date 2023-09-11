package core.repository.item;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.dto.client.response.item.ItemCountPerItemCategoryResponseDto;
import lombok.RequiredArgsConstructor;

import static core.domain.item.QItem.item;
import static core.domain.item.QItemCategory.itemCategory;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ItemCategoryRepositoryCustomImpl implements ItemCategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemCountPerItemCategoryResponseDto> findItemCountPerItemCategoryResponseDtoAllByItemCategoryIds(Collection<Long> itemCategoryIds) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ItemCountPerItemCategoryResponseDto.class,
                                itemCategory.id.as("itemCategoryId"),
                                itemCategory.count().as("itemCount")
                        )
                )
                .from(itemCategory)
                .innerJoin(item)
                .on(item.itemCategoryId.eq(itemCategory.id))
                .where(
                        itemCategory.id.in(itemCategoryIds)
                )
                .groupBy(itemCategory.id)
                .fetch();
    }
}
