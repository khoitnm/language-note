package org.tnmk.ln.infrastructure.tts.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.filestorage.FileItemService;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;

/**
 * @author khoi.tran on 2/2/17.
 */
@Service
public class TtsItemService {
    private static final String MIMETYPE_MP3 = "audio/mpeg";
    @Autowired
    private FileItemService fileItemService;

    @Autowired
    private TtsItemRepository ttsItemRepository;

    /**
     * @param locale       en-us, en-in,...
     * @param originalText
     * @return
     */
    public TtsItem findText(String locale, String originalText) {
        String text = cleanupText(originalText);
        return ttsItemRepository.findOneByLocaleAndText(locale, text);
    }

    public String cleanupText(String originalText) {
        return originalText.trim().toLowerCase();
    }

    public TtsItem putText(String locale, String originalText, String source, byte[] mp3Data) {
        String text = cleanupText(originalText);
        TtsItem ttsItem = findText(locale, text);
        if (ttsItem == null) {
            ttsItem = new TtsItem();
        }

        FileItem oldFileItem = ttsItem.getFileItem();
        if (oldFileItem != null) {
            fileItemService.remove(oldFileItem.getId());
        }

        FileItem newFileItem = fileItemService.save(text + "_" + locale + ".mp3", MIMETYPE_MP3, mp3Data, mp3Data.length);
        ttsItem.setSource(source);
        ttsItem.setFileItem(newFileItem);
        ttsItem.setText(text);
        ttsItem.setLocale(locale);
        return ttsItemRepository.save(ttsItem);
    }
}
