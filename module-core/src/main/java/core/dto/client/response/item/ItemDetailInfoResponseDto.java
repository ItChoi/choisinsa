package core.dto.client.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailInfoResponseDto {
    private ItemResponseDto item;
    private List<ItemOptionResponseDto> itemOptions;
    private ItemEditorInfoResponseDto itemEditorInfo;
    private List<ItemHashTagResponseDto> itemHashTags;
}
