package org.tnmk.ln.client.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.util.ObjectMapperUtil;
import org.tnmk.common.util.http.RestTemplateHelper;
import org.tnmk.ln.client.common.security.model.User;

@Service
public class AuthenticationServerProxy {
    private String clientId = "trusted-app";
    private String clientSecret = "secret";
    private String grantType = "password";

    //    private String urlOauth2Token = "http://localhost:8080/language-note-server/oauth/token";
    private String urlOauth2Token = "http://localhost:8080/oauth/token";
    private String urlUserInfo = "http://localhost:8080/me";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    public OAuth2AccessTokenResponse login(String username, String password) {
//        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = constructHeaderWithClientProfile();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("username", username);
        formData.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        //I don't want to convert directly from httpResponse to object because I would like to track the string response whenever there's any error.
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlOauth2Token, httpEntity, String.class);
        return restTemplateHelper.convertJsonResponseEntity(responseEntity, OAuth2AccessTokenResponse.class);
    }

    public User getMyProfile(String accessToken){
        HttpHeaders headers = constructJsonHeaderWithAccessToken(accessToken);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlUserInfo, HttpMethod.GET, httpEntity, String.class);
        return restTemplateHelper.convertJsonResponseEntity(responseEntity, User.class);
    }

    private HttpHeaders constructHeaderWithClientProfile(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplateHelper.addBasicAuthenticationHeader(headers, clientId, clientSecret);
        return headers;
    }
    private HttpHeaders constructJsonHeaderWithAccessToken(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        RestTemplateHelper.addAccessTokenHeader(headers, accessToken);
        return headers;
    }

}