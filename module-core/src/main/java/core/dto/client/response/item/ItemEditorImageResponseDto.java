package core.dto.client.response.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditorImageResponseDto {
    private Long id;
    private String fileUrl;
    private String filename;
}
