package core.common.mapper.item;

import core.domain.item.ItemEditorInfo;
import core.dto.client.response.item.ItemEditorInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemEditorInfoMapper {
    ItemEditorInfoMapper INSTANCE = Mappers.getMapper(ItemEditorInfoMapper.class);

    ItemEditorInfoResponseDto toItemEditorInfoResponseDto(ItemEditorInfo itemEditorInfo);
}
