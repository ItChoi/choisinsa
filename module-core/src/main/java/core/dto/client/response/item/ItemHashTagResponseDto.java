package core.dto.client.response.item;

import core.domain.hashtag.HashTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemHashTagResponseDto {
    private Long id;
    private String content;

    public ItemHashTagResponseDto(HashTag hashTag) {
        this.id = hashTag.getId();
        this.content = hashTag.getContent();
    }
}
