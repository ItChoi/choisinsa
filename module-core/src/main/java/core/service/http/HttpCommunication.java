package core.service.http;

import core.dto.kakao.dto.request.KakaoAuthorizeCodeRequestDto;
import org.springframework.http.MediaType;

public interface HttpCommunication {

    Object requestPost(String url,
                       MediaType mediaType,
                       Object requestDtos);


    Object requestGet(String oauthTokenUrl, Object requestDtos);
}
