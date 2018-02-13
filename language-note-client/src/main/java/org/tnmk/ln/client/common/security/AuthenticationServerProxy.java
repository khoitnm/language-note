package org.tnmk.ln.client.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.util.ObjectMapperUtil;

import java.nio.charset.Charset;

@Service
public class AuthenticationServerProxy {
    private String clientId = "trusted-app";
    private String clientSecret = "secret";
    private String grantType = "password";

    //    private String urlOauth2Token = "http://localhost:8080/language-note-server/oauth/token";
    private String urlOauth2Token = "http://localhost:8080/oauth/token";

    @Autowired
    private ObjectMapper objectMapper;

    public OAuth2AccessTokenResponse login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        addBasicAuthenticationHeader(headers, clientId, clientSecret);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("username", username);
        formData.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlOauth2Token, httpEntity, String.class);
        String responseJson = responseEntity.getBody();
        OAuth2AccessTokenResponse oAuth2AccessTokenResponse = ObjectMapperUtil.toObject(objectMapper, responseJson, OAuth2AccessTokenResponse.class);
        return oAuth2AccessTokenResponse;
    }

    public static void addBasicAuthenticationHeader(HttpHeaders httpHeaders, String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        httpHeaders.set("Authorization", authHeader);
    }
}