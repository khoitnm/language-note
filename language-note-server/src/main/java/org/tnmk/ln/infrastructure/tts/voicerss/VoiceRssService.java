package org.tnmk.ln.infrastructure.tts.voicerss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.ln.infrastructure.tts.cache.TtsItemService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * http://www.voicerss.org/api/documentation.aspx
 *
 * @author khoi.tran on 2/2/17.
 */
@Service
public class VoiceRssService {
    @Autowired
    private TtsItemService ttsItemService;

    @Value("${tts.voicerss.api.key}")
    private String apiKey;

    private static final String HOST = "http://api.voicerss.org";
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * @param sourceLocale view more at {@literal http://www.voicerss.org/api/documentation.aspx}
     * @param originalText
     * @return
     */
    public byte[] toSpeech(String sourceLocale, String originalText) {
        try {
            String text = ttsItemService.cleanupText(originalText);

            MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();
            translateBody.add("src", text);
            translateBody.add("hl", sourceLocale);
            translateBody.add("key", apiKey);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            URI uri;
            try {
                uri = new URI(HOST + "/");
            } catch (URISyntaxException e) {
                throw new UnexpectedException(e.getMessage(), e);
            }
            RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.POST, uri);

            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
            return responseEntity.getBody();
        } catch (ResourceAccessException ex) {
            throw new UnexpectedException("Cannot connect to external Text-To-Speech Service.", ex);
        }
//        return null;
    }
}
