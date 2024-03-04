package core.dto.general;

import com.mall.choisinsa.security.dto.ReissueTokenDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreReissueTokenDto {

    @NotBlank
    private String expiredAccessToken;

    public ReissueTokenDto convert() {
        return new ReissueTokenDto(expiredAccessToken);
    }
}
