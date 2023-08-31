package core.dto.client.response.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemEditorInfoResponseDto {
    private Long id;
    private String title;
    private Boolean isMain;
    private List<ItemEditorContentResponseDto> itemEditorContents;
}
