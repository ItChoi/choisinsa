package core.common.http;

import core.common.enumeration.authority.AuthorizationType;
import org.springframework.http.MediaType;

public interface HttpCommunication {

    Object requestPost(String url,
                       MediaType mediaType,
                       Object requestDtos);


    Object requestGet(String url,
                      Object data);

    Object requestPostWithToken(String url,
                                MediaType mediaType,
                                AuthorizationType type,
                                String token);
}
