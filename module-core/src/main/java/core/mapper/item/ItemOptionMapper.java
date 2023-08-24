package core.mapper.item;

import core.domain.item.ItemOption;
import core.dto.admin.request.item.ItemOptionRequestDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemOptionMapper {

    ItemOptionMapper INSTANCE = Mappers.getMapper(ItemOptionMapper.class);

    void updateItemOption(@MappingTarget ItemOption itemOption,
                          ItemOptionRequestDto dto);

}
