package org.tnmk.common.infrastructure.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author khoi.tran on 4/28/17.
 */
public class RestTemplateUtil {
    public static HttpEntity<String> getDownloadRequest(String url, Map<String, String> headersProperties) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        for (String headerKey : headersProperties.keySet()) {
            headers.add(headerKey, headersProperties.get(headerKey));
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, "1");
    }
}
