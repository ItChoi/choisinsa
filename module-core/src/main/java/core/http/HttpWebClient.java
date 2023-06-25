package core.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.common.secret.ConstData;
import com.mall.choisinsa.enumeration.authority.AuthorizationType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import io.reactivex.rxjava3.functions.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;


@Service
public class HttpWebClient implements HttpCommunication {

    @Override
    public Object requestGet(String url,
                             Object data) {
        if (!StringUtils.hasText(url)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return WebClient.builder()
                .baseUrl(url)
                .uriBuilderFactory(getDefaultUriBuilderFactory(url, EncodingMode.VALUES_ONLY))
                .build()
                .get()
                .uri(getUriBuilderInGetForContentType(data))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    @Override
    public Object requestPost(String url,
                              MediaType mediaType,
                              Object datas) {
        if (!StringUtils.hasText(url) || mediaType == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return initWebClientForPostRestApi(url, mediaType)
                .post()
                .body(getBodyInPostForContentType(mediaType, datas))
                .retrieve()
                .bodyToMono(Object.class)
                .block();

    }

    @Override
    public Object requestPostWithToken(String url,
                                       MediaType mediaType,
                                       AuthorizationType type,
                                       String token) {
        if (!StringUtils.hasText(url) || type == null || !StringUtils.hasText(token)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return initWebClientForPostRestApi(url, mediaType)
                .post()
                .header(type.getType(), getFormattingTokenWithAuthType(type, token))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    private String getFormattingTokenWithAuthType(AuthorizationType type,
                                                  String token) {
        if (type == AuthorizationType.AUTHORIZATION) {
            return ConstData.AUTHORIZATION_BEARER + token;
        }

        return token;
    }

    private WebClient initWebClientForPostRestApiWithToken(String url,
                                                           MediaType mediaType,
                                                           AuthorizationType type,
                                                           String token) {
        if (type == null || !StringUtils.hasText(token)) {
            return initWebClientForPostRestApi(url, mediaType);
        }

        if (mediaType == null) {
            return WebClient.builder()
                    .baseUrl(url)
                    .defaultHeader(type.getType(), token)
                    .build();
        }

        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(type.getType(), token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, getMediaTypeValue(mediaType))
                .build();
    }

    private WebClient initWebClientForPostRestApi(String url,
                                                  MediaType mediaType) {
        if (mediaType == null) {
            return WebClient.builder()
                    .baseUrl(url)
                    .build();
        }

        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, getMediaTypeValue(mediaType))
                .build();
    }

    private String getMediaTypeValue(MediaType mediaType) {
        return mediaType.getType() + "/" + mediaType.getSubtype();
    }

    private BodyInserter getBodyInPostForContentType(MediaType mediaType,
                                                     Object requestDtos) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(requestDtos);
            JsonNode jsonNode = objectMapper.readTree(jsonString);


            if (MediaType.APPLICATION_JSON == mediaType || MediaType.APPLICATION_JSON_UTF8 == mediaType) {
                return BodyInserters.fromValue(jsonString);
            }

            if (MediaType.MULTIPART_FORM_DATA == mediaType) {

            }

            if (MediaType.APPLICATION_FORM_URLENCODED == mediaType) {
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

                Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> next = fields.next();
                    formData.add(next.getKey(), next.getValue().asText());
                }
                return BodyInserters.fromFormData(formData);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
    }

    private String getUriBuilderInGetForContentType(Object requestDtos) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(requestDtos);
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            Function<UriBuilder, URI> uriBuilderFunction;
                uriBuilderFunction = uriBuilder -> {
                    System.out.println("---test---");
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> next = fields.next();
                        uriBuilder.queryParam(next.getKey(), next.getValue().asText());
                    }
                    URI build = uriBuilder.build();
                    return build;
                };

            System.out.println("test2: " + uriBuilderFunction.toString());
            return uriBuilderFunction.toString();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static DefaultUriBuilderFactory getDefaultUriBuilderFactory(String url, EncodingMode encodingMode) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
        factory.setEncodingMode(encodingMode);
        return factory;
    }
}
