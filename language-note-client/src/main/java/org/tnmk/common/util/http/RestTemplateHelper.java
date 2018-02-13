package org.tnmk.common.util.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.tnmk.common.util.ObjectMapperUtil;

import java.nio.charset.Charset;


@Component
public class RestTemplateHelper {
    private final ObjectMapper objectMapper;
    @Autowired
    public RestTemplateHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static void addBasicAuthenticationHeader(HttpHeaders httpHeaders, String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        httpHeaders.set("Authorization", authHeader);
    }
    public static void addAccessTokenHeader(HttpHeaders httpHeaders, String accessToken) {
        httpHeaders.set("Authorization", "Bearer "+accessToken);
    }

    /**
     * <p>
     * From {@link org.springframework.web.client.RestTemplate}, you can call http request and then convert json response directly to object.<br/>
     * But when there's error, you cannot know which string response is.<br/>
     * So I usually convert json response to string.<br/>
     * And then use this method to convert string to object. Then if there's something wrong when converting, I will know what's the original string response.<br/>
     * </p>
     *
     * @param responseEntity
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T convertJsonResponseEntity(ResponseEntity<String> responseEntity, Class<T> clazz){
        String responseJson = responseEntity.getBody();
        return ObjectMapperUtil.toObject(objectMapper, responseJson, clazz);
    }
}
