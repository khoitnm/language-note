package tnmk.ln.infrastructure.dictionary.oxford;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tnmk.common.exception.UnexpectedException;
import tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordResponse;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author khoi.tran on 2/24/17.
 */
@Service
public class OxfordService {
    private static final String FROM_REQUEST_ENTRIES = "entries";
    private static final String FROM_REQUEST_SENTENCES = "entries/sentences";

    @Value("${dictionary.oxford.app.id}")
    private String appId;

    @Value("${dictionary.oxford.app.key}")
    private String appKey;

    @Autowired
    private OxfordWordRepositories oxfordWordRepositories;

    public OxfordWord lookUpDefinition(String sourceLanguage, String word) {
        List<OxfordWord> oxfordWords = oxfordWordRepositories.findByLanguageAndWordAndFromRequest(sourceLanguage, word, FROM_REQUEST_ENTRIES);
        if (!oxfordWords.isEmpty()) return oxfordWords.get(0);

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();
//        String translateLanguage = sourceLanguage + "-" + destLanguage;
//        translateBody.add("text", originalText);
//        translateBody.add("lang", translateLanguage);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri;
        try {
            String uriString = String.format("https://od-api.oxforddictionaries.com/api/v1/entries/%s/%s", sourceLanguage, word);
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        ResponseEntity<OxfordResponse> responseEntity = restTemplate.exchange(requestEntity, OxfordResponse.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
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

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri;
        try {
            String uriString = String.format("https://od-api.oxforddictionaries.com/api/v1/entries/%s/%s/sentences", sourceLanguage, wordId);
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        ResponseEntity<OxfordResponse> responseEntity = restTemplate.exchange(requestEntity, OxfordResponse.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
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
}
