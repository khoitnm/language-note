package org.tnmk.ln.infrastructure.translate.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author khoi.tran on 2/1/17.
 */
@Service
public class TranslateCacheService {

    @Autowired
    private TranslationRepository translatedItemRepository;

    public Translation put(String sourceLanguage, String destLanguage, String sourceText, String translatedText) {
        String cleanSourceText = cleanSourceText(sourceText);
        Translation translatedItem = get(sourceLanguage, destLanguage, cleanSourceText);
        if (translatedItem == null) {
            translatedItem = new Translation(sourceLanguage, destLanguage, cleanSourceText, translatedText);
        }
        translatedItem.setTranslatedText(translatedText);
        return translatedItemRepository.save(translatedItem);
    }

    public Translation get(String sourceLanguage, String destLanguage, String sourceText) {
        String cleanSourceText = cleanSourceText(sourceText);
        return translatedItemRepository.findOneBySourceLanguageAndDestLanguageAndSourceText(sourceLanguage, destLanguage, cleanSourceText);
    }

    /**
     * @param sourceText
     * @return Only trim it, don't cast case because uppercase words can have different meaning from lowercase words.
     */
    private String cleanSourceText(String sourceText) {
        return sourceText.trim();
    }
}