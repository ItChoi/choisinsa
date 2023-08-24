package core.mapper.item;

import core.domain.item.Item;
import core.domain.item.ItemDetail;
import core.dto.admin.request.item.ItemDetailRequestDto;
import core.dto.admin.request.item.ItemRequestDto;
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
                    ItemRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateItemDetail(@MappingTarget ItemDetail itemDetail,
                          ItemDetailRequestDto step2Info);
}
