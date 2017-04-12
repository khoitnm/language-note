package tnmk.ln.infrastructure.translate.cache;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;

/**
 * @author khoi.tran on 2/1/17.
 */
@Document(collection = "Translation")
public class Translation extends BaseMongoEntity {
    @Indexed
    private String sourceLanguage;
    @Indexed
    private String destLanguage;
    @Indexed
    private String sourceText;
    private String translatedText;

    public Translation() {}

    public Translation(String sourceLanguage, String destLanguage, String sourceText, String translatedText) {
        this.sourceLanguage = sourceLanguage;
        this.destLanguage = destLanguage;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getDestLanguage() {
        return destLanguage;
    }

    public void setDestLanguage(String destLanguage) {
        this.destLanguage = destLanguage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
