package org.tnmk.ln.infrastructure.dictionary.oxford;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordResponse;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 2/24/17.
 */
@Service
public class OxfordService {
    public static final Logger LOGGER = LoggerFactory.getLogger(OxfordService.class);
    /**
     * This is the Oxford API which the result was got from.
     */
    private static final String FROM_REQUEST_ENTRIES = "entries";
    private static final String FROM_REQUEST_SENTENCES = "entries/sentences";

    @Value("${dictionary.oxford.app.id}")
    private String appId;

    @Value("${dictionary.oxford.app.key}")
    private String appKey;

    @Autowired
    private OxfordWordRepositories oxfordWordRepositories;

    @Autowired
    private OxfordAudioService oxfordAudioService;

    public OxfordWord lookUpDefinition(String sourceLanguage, String word) {
        List<OxfordWord> oxfordWords = oxfordWordRepositories.findByLanguageAndWordAndFromRequest(sourceLanguage, word, FROM_REQUEST_ENTRIES);
        if (!oxfordWords.isEmpty()) return oxfordWords.get(0);

        RestTemplate restTemplate = new RestTemplate();
        String uriString = String.format("https://od-api.oxforddictionaries.com/api/v1/entries/%s/%s", sourceLanguage, word);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(uriString);
        ResponseEntity<OxfordResponse> responseEntity=exchange(restTemplate, requestEntity, OxfordResponse.class);
        if (responseEntity == null){
            return null;
        }
        OxfordResponse oxfordResponse = responseEntity.getBody();
        oxfordWords = oxfordResponse.getResults();
        OxfordWord result = null;
        if (!oxfordWords.isEmpty()) {
            result = oxfordWords.get(0);
            //Must try-catch here because I want to always able to save the look up result before exiting this method.
            try {
                //Merge sentences
                OxfordWord wordSentenceExamples = findSentenceExamples(sourceLanguage, word);
                if (wordSentenceExamples != null && !CollectionUtils.isEmpty(wordSentenceExamples.getLexicalEntries())) {
                    List<LexicalEntry> lexicalEntriesInSentences = wordSentenceExamples.getLexicalEntries();
                    List<LexicalEntry> lexicalEntriesInFoundWord = result.getLexicalEntries();
                    for (LexicalEntry lexicalEntryInFoundWord : lexicalEntriesInFoundWord) {
                        for (LexicalEntry lexicalEntryInSentence : lexicalEntriesInSentences) {
                            if (lexicalEntryInFoundWord.getText().equals(lexicalEntryInSentence.getText()) && lexicalEntryInFoundWord.getLexicalCategory().equals(lexicalEntryInSentence.getLexicalCategory())) {
                                lexicalEntryInFoundWord.setSentences(lexicalEntryInSentence.getSentences());
                            }
                        }
                    }
                }
                for (OxfordWord oxfordWord : oxfordWords) {
                    oxfordWord.setFromRequest(FROM_REQUEST_ENTRIES);
                    oxfordAudioService.downloadAndSaveAudios(oxfordWord);
                }
            } catch (Exception ex) {
                throw new UnexpectedException(ex.getMessage(), ex);
            } finally {
                oxfordWordRepositories.save(oxfordWords);
            }
        }
        return result;
    }

    public OxfordWord findSentenceExamples(String sourceLanguage, String wordId) {
        RestTemplate restTemplate = new RestTemplate();
        String uriString = String.format("https://od-api.oxforddictionaries.com/api/v1/entries/%s/%s/sentences", sourceLanguage, wordId);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(uriString);
        ResponseEntity<OxfordResponse> responseEntity = exchange(restTemplate, requestEntity,  OxfordResponse.class);
        if (responseEntity == null) {
            return null;
        }
        OxfordResponse oxfordResponse = responseEntity.getBody();
        List<OxfordWord> oxfordWords = oxfordResponse.getResults();
        OxfordWord result = null;
        if (!oxfordWords.isEmpty()) {
            for (OxfordWord oxfordWord : oxfordWords) {
                oxfordWord.setFromRequest(FROM_REQUEST_SENTENCES);
            }
            oxfordWordRepositories.save(oxfordWords);
            result = oxfordWords.get(0);
        }
        return result;
    }

    private RequestEntity<MultiValueMap<String, Object>> createRequestEntity(String uriString) {

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri = OxfordRestUtil.createUri(uriString);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        return requestEntity;
    }

    /**
     * When using {@link RestTemplate}, if the result is 404 (NOT FOUND), it will not return null.
     * It will throw an exception instead. And I find that behaviour is very annoying.
     * So this method will help you to avoid that Exception. If not found, the result is null.
     * @param restTemplate
     * @param requestEntity
     * @param responseClass
     * @param <R>
     * @return
     */
    public static <R> ResponseEntity<R> exchange(RestTemplate restTemplate,RequestEntity<MultiValueMap<String, Object>> requestEntity, Class<R> responseClass){
        try {
            return restTemplate.exchange(requestEntity, responseClass);
        }catch (HttpClientErrorException ex){
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }else{
                throw new UnexpectedException("Cannot call API to OxfordService: "+ex.getMessage(), ex);
            }
        }
    }
}
