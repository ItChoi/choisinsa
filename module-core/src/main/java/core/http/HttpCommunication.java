package core.http;

import org.springframework.http.MediaType;

public interface HttpCommunication {

    Object requestPost(String url,
                       MediaType mediaType,
                       Object requestDtos);


    Object requestGet(String oauthTokenUrl, Object requestDtos);
}
