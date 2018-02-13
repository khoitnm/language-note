package org.tnmk.ln.infrastructure.translate.yandex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.ln.infrastructure.translate.cache.TranslateCacheService;
import org.tnmk.ln.infrastructure.translate.cache.Translation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author khoi.tran on 2/1/17.
 */
@Service
public class YandexTranslationService {
    @Value("${translate.yandex.api.key}")
    private String apiKey;

    @Autowired
    private TranslateCacheService translateCacheService;

    public Translation translate(String sourceLanguage, String destLanguage, String originalText) {
        Translation translatedItem = translateCacheService.get(sourceLanguage, destLanguage, originalText);
        if (translatedItem != null) {
            return translatedItem;
        }

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();
        String translateLanguage = sourceLanguage + "-" + destLanguage;
        translateBody.add("text", originalText);
        translateBody.add("lang", translateLanguage);
        translateBody.add("key", apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        URI uri = null;
        try {
            uri = new URI("https://translate.yandex.net/api/v1.5/tr.json/translate");
        } catch (URISyntaxException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.POST, uri);
        ResponseEntity<TranslateResult> responseEntity = restTemplate.exchange(requestEntity, TranslateResult.class);
        TranslateResult result = responseEntity.getBody();
        List<String> translatedTexts = result.getText();
        String translatedText = translatedTexts.get(0);
        return translateCacheService.put(sourceLanguage, destLanguage, originalText, translatedText);
    }

    private static class TranslateResult {
        private String code;
        private String lang;
        private List<String> text;

        public TranslateResult() {
        }

        public TranslateResult(String code, String lang, List<String> text) {
            this.text = text;
            this.code = code;
            this.lang = lang;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public List<String> getText() {
            return text;
        }

        public void setText(List<String> text) {
            this.text = text;
        }
    }
}
