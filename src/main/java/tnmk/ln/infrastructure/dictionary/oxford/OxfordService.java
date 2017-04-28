package tnmk.ln.infrastructure.dictionary.oxford;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.client.RestTemplate;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.util.FileUtil;
import tnmk.common.util.MimeTypeUtil;
import tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordResponse;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import tnmk.ln.infrastructure.dictionary.oxford.entity.Pronunciation;
import tnmk.ln.infrastructure.filestorage.entity.FileItem;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 2/24/17.
 */
@Service
public class OxfordService {
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
    private OxfordAudioRepositories oxfordAudioRepositories;

    public OxfordWord lookUpDefinition(String sourceLanguage, String word) {
        List<OxfordWord> oxfordWords = oxfordWordRepositories.findByLanguageAndWordAndFromRequest(sourceLanguage, word, FROM_REQUEST_ENTRIES);
        if (!oxfordWords.isEmpty()) return oxfordWords.get(0);

        RestTemplate restTemplate = new RestTemplate();
        String uriString = String.format("https://od-api.oxforddictionaries.com/api/v1/entries/%s/%s", sourceLanguage, word);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(uriString);
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
                    downloadAndSaveAudios(oxfordWord);
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

    public List<OxfordAudio> downloadAndSaveAudios(OxfordWord oxfordWord) {
        List<OxfordAudio> result = new ArrayList<>();
        List<LexicalEntry> lexicalEntries = oxfordWord.getLexicalEntries();
        for (LexicalEntry lexicalEntry : lexicalEntries) {
            List<Pronunciation> pronunciations = lexicalEntry.getPronunciations();
            if (pronunciations == null) continue;
            for (Pronunciation pronunciation : pronunciations) {
                if (pronunciation == null || StringUtils.isBlank(pronunciation.getAudioFile())) continue;
                OxfordAudio oxfordAudio = downloadOxfordAudio(oxfordWord, lexicalEntry, pronunciation);
                result.add(oxfordAudio);
            }
        }
        oxfordAudioRepositories.save(result);
        return result;
    }

    /**
     * If not exist in DB, download form OxfordDictionary.
     *
     * @param oxfordWord
     * @param lexicalEntry
     * @param pronunciation
     * @return
     */
    private OxfordAudio downloadOxfordAudio(OxfordWord oxfordWord, LexicalEntry lexicalEntry, Pronunciation pronunciation) {
        String fileUrl = pronunciation.getAudioFile();
        OxfordAudio oxfordAudio = oxfordAudioRepositories.findByOriginalUrl(fileUrl);
        if (oxfordAudio != null) return oxfordAudio;

        OxfordAudio result = new OxfordAudio();
        String cleanWord = cleanupText(oxfordWord.getWord());
        result.setWord(cleanWord);
        result.setLanguage(lexicalEntry.getLanguage());
        result.setLexicalCategory(lexicalEntry.getLexicalCategory());
        result.setDialects(pronunciation.getDialects());
        result.setPhoneticNotation(pronunciation.getPhoneticNotation());
        result.setPhoneticSpelling(pronunciation.getPhoneticSpelling());

        byte[] fileBinary = downloadFile(fileUrl);
        String fileExtension = FileUtil.getFileExtension(fileUrl);
        String fileMimeType = MimeTypeUtil.getMimeTypeFromFileExtension(fileExtension);

        FileItem fileItem = new FileItem();
        fileItem.setBytesContent(fileBinary);
        fileItem.setFileSize((long) fileBinary.length);
        fileItem.setMimeType(fileMimeType);
        fileItem.setName(cleanWord);

        result.setOriginalUrl(fileUrl);
        result.setFileItem(fileItem);
        return result;
    }

    public String cleanupText(String originalText) {
        return originalText.trim().toLowerCase();
    }

    public byte[] downloadFile(String url) {
        RequestEntity<MultiValueMap<String, Object>> requestEntity = createRequestDownloadFile(url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
        byte[] data = responseEntity.getBody();
        return data;
    }

    private RequestEntity<MultiValueMap<String, Object>> createRequestEntity(String uriString) {

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri = createUri(uriString);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        return requestEntity;
    }

    private RequestEntity<MultiValueMap<String, Object>> createRequestDownloadFile(String uriString) {

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri = createUri(uriString);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        return requestEntity;
    }

    private URI createUri(String uriString) {
        try {
            return new URI(uriString);
        } catch (URISyntaxException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }
}
