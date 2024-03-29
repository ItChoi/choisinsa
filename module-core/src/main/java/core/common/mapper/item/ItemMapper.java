package core.common.mapper.item;

import core.domain.item.Item;
import core.domain.item.ItemDetail;
import core.dto.admin.request.item.AdminItemDetailRequestDto;
import core.dto.admin.request.item.AdminItemRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "fileUrl", ignore = true)
    @Mapping(target = "filename", ignore = true)
    @Mapping(source = "itemNameEn", target = "nameEn")
    @Mapping(source = "itemNameKo", target = "nameKo")
    void updateItem(@MappingTarget Item item,
                    AdminItemRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateItemDetail(@MappingTarget ItemDetail itemDetail,
                          AdminItemDetailRequestDto step2Info);
}
