package core.repository.stats;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.dto.client.response.stats.PriorityItemSalesStatsPerItemCategoryResponseDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static core.domain.item.QItem.item;
import static core.domain.item.QItemCategory.itemCategory;
import static core.domain.stats.QItemSalesStats.itemSalesStats;

@RequiredArgsConstructor
public class ItemSalesStatsRepositoryCustomImpl implements ItemSalesStatsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PriorityItemSalesStatsPerItemCategoryResponseDto> findItemSalesStatsPerItemCategoryResponseDtoAllBy(Integer year) {
        NumberPath<Long> totalQuantity = Expressions.numberPath(Long.class, "totalQuantity");

        return queryFactory
                .select(
                        Projections.fields(
                                PriorityItemSalesStatsPerItemCategoryResponseDto.class,
                                itemCategory.rootParentId.as("rootItemCategoryId"),
                                itemCategory.parentId.as("parentItemCategoryId"),
                                itemCategory.id.as("itemCategoryId"),
                                item.id.as("itemId"),
                                itemSalesStats.salesQuantity.sum().as("totalQuantity")
                        )
                )
                .from(itemSalesStats)
                .innerJoin(item)
                .on(item.id.eq(itemSalesStats.itemId))
                .innerJoin(itemCategory)
                .on(itemCategory.id.eq(item.itemCategoryId))
                .where(
                        eqSalesDate(year)
                )
                .groupBy(item.id)
                .orderBy(totalQuantity.desc())
                .fetch();
    }

    private BooleanExpression eqSalesDate(Integer year) {
        return eqYear(year);
    }

    private BooleanExpression eqYear(Integer year) {
        if (year == null) {
            return null;
        }

        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year + 1, 1, 1, 0, 0, 0);

        return itemSalesStats.salesDate.goe(startDate).and(itemSalesStats.salesDate.lt(endDate));
    }
}
