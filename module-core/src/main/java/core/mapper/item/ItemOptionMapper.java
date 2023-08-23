package core.mapper.item;

import core.domain.item.ItemOption;
import core.dto.admin.request.item.ItemOptionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemOptionMapper {

    ItemOptionMapper INSTANCE = Mappers.getMapper(ItemOptionMapper.class);

    void updateItemOption(@MappingTarget ItemOption itemOption,
                          ItemOptionRequestDto dto);

}
