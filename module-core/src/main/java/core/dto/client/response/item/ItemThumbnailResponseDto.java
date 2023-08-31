package core.dto.client.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemThumbnailResponseDto {
    private Long id;
    private int displayOrder;
    private String fileUrl;
    private String filename;
}
