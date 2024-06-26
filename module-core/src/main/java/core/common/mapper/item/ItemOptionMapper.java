package core.common.mapper.item;

import core.domain.item.ItemOption;
import core.dto.admin.request.item.AdminItemOptionRequestDto;
import core.dto.client.response.item.ItemOptionResponseDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


/*@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)*/
@Mapper
public interface ItemOptionMapper {

    ItemOptionMapper INSTANCE = Mappers.getMapper(ItemOptionMapper.class);

    void updateItemOption(@MappingTarget ItemOption itemOption,
                          AdminItemOptionRequestDto dto);

    ItemOptionResponseDto toItemOptionResponseDto(ItemOption itemOption);
}
