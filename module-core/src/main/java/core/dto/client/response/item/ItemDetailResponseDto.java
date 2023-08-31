package core.dto.client.response.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponseDto {
    private Long id;
    private String itemNumber;
    private String materialName;
    private String manufacturer;
    private String manufacturerCountryName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingDate; // 제조연월
    private String warrantyPeriod;
}
