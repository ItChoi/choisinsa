package core.service.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
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
                             Object requestDtos) {
        if (!StringUtils.hasText(url)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return WebClient.builder()
                .baseUrl(url)
                .uriBuilderFactory(getDefaultUriBuilderFactory(url, EncodingMode.VALUES_ONLY))
                .build()
                .get()
                .uri(getUriBuilderInGetForContentType(requestDtos))
                /*.uri(uriBuilder -> uriBuilder
                        .queryParam("client_id", dto.getClient_id())
                        .queryParam("redirect_uri", dto.getRedirect_uri())
                        .queryParam("response_type", dto.getResponse_type())
                        .build())*/
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    @Override
    public Object requestPost(String url,
                              MediaType mediaType,
                              Object requestDtos) {
        if (!StringUtils.hasText(url) || mediaType == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        WebClient webClient = initWebClientForPostRestApi(url, mediaType);
        return webClient.post()
                .body(getBodyInPostForContentType(mediaType, requestDtos))
                .retrieve()
                .bodyToMono(Object.class)
                .block();

    }

    private WebClient initWebClientForPostRestApi(String url,
                                                  MediaType mediaType) {

        String mediaTypeValue = getMediaTypeValue(mediaType);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, mediaTypeValue)
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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
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
