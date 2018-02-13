package org.tnmk.ln.infrastructure.translate.google;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * @author khoi.tran on 2/1/17.
 */
public class GoogleTranslateService {

    public static String translateText(String sourceText) {
        Translate service = TranslateOptions.newBuilder().setApiKey("AIzaSyA6yFgxn-ZedxCghH1X4xwuLLcltaUlK3c").setTargetLanguage("english").build().getService();
        Translation translation = service.translate(sourceText);
        return translation.getTranslatedText();
    }
}
