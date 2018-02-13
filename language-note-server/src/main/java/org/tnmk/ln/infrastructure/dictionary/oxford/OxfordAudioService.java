package org.tnmk.ln.infrastructure.dictionary.oxford;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tnmk.common.util.FileUtil;
import org.tnmk.common.util.MimeTypeUtil;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Pronunciation;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;
import org.tnmk.ln.infrastructure.tts.TextToSpeechService;
import org.tnmk.ln.infrastructure.tts.cache.TtsItemService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 4/29/17.
 */
@Service
public class OxfordAudioService {
    public static final Logger LOGGER = LoggerFactory.getLogger(OxfordAudioService.class);

    @Value("${dictionary.oxford.app.id}")
    private String appId;

    @Value("${dictionary.oxford.app.key}")
    private String appKey;
    @Autowired
    private OxfordAudioRepositories oxfordAudioRepositories;

    //TODO should not involved in TextToSpeechService. It should be put in another aggregated service.
    @Autowired
    private TtsItemService ttsItemService;

    @Async
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

        replaceTTSByOxford(result);
        return result;
    }

    public void replaceTTSByOxford(OxfordAudio oxfordAudio) {
        String ttsLocale;
        String oxfordWordLanguage = oxfordAudio.getLanguage();
        if (oxfordWordLanguage.equalsIgnoreCase("en")) {
            ttsLocale = "en-us";
        } else {
            return;
        }
        LOGGER.debug("Replace tts \nLanguage: {}, Locale: {}, text: {}", oxfordWordLanguage, ttsLocale, oxfordAudio.getWord());
        ttsItemService.putText(ttsLocale, oxfordAudio.getWord(), TextToSpeechService.TTS_SOURCE_OXFORD_DICTIONARY, oxfordAudio.getFileItem().getBytesContent());
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

    private RequestEntity<MultiValueMap<String, Object>> createRequestDownloadFile(String uriString) {

        MultiValueMap<String, Object> translateBody = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.add("app_id", appId);
        headers.add("app_key", appKey);

        URI uri = OxfordRestUtil.createUri(uriString);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(translateBody, headers, HttpMethod.GET, uri);
        return requestEntity;
    }
}
