package core.repository.item;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static core.domain.item.QItem.item;
import static core.domain.item.QItemDetail.itemDetail;
import static core.domain.item.QItemImage.itemImage;
import static core.domain.item.QItemThumbnail.itemThumbnail;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public ItemResponseDto findItemResponseDtoBy(Long itemId,
                                                  ItemDetailRequestDto requestDto) {
        ItemResponseDto result = queryFactory
                .select(
                        Projections.constructor(ItemResponseDto.class,
                                item.id,
                                item.itemCategoryId,
                                item.brandId,
                                item.status,
                                item.targetType,
                                item.nameEn,
                                item.nameKo,
                                item.price,
                                item.fileUrl,
                                item.filename,
                                item.totalStockQuantity,
                                Projections.constructor(ItemDetailResponseDto.class,
                                        itemDetail.id,
                                        itemDetail.itemNumber,
                                        itemDetail.materialName,
                                        itemDetail.manufacturer,
                                        itemDetail.manufacturerCountryName,
                                        itemDetail.manufacturingDate,
                                        itemDetail.warrantyPeriod
                                )
                        )
                )
                .from(item)
                .leftJoin(itemDetail)
                .on(itemDetail.itemId.eq(item.id))
                .where(
                        item.id.eq(itemId),
                        item.brandId.eq(requestDto.getBrandId())
                ).fetchOne();

        injectItemThumbnails(result);
        return result;
    }

    private void injectItemThumbnails(ItemResponseDto responseDto) {
        if (responseDto != null) {
            List<ItemThumbnailResponseDto> itemThumbnailResponseDtos = queryFactory
                    .select(Projections.constructor(ItemThumbnailResponseDto.class,
                                    itemThumbnail.id,
                                    itemThumbnail.displayOrder,
                                    itemThumbnail.fileUrl,
                                    itemThumbnail.filename
                            )
                    ).from(itemImage)
                    .leftJoin(itemThumbnail)
                    .on(itemThumbnail.itemImageId.eq(itemImage.id))
                    .where(
                            itemImage.itemId.eq(responseDto.getId())
                    ).orderBy(
                            itemThumbnail.displayOrder.asc()
                    )
                    .fetch();

            responseDto.setItemThumbnails(itemThumbnailResponseDtos);
        }
    }

}
