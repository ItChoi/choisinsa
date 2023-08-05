package core.dto.admin.request.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ItemThumbnailImageRequestDto {
    private int displayOrder;
    private MultipartFile file;
}
