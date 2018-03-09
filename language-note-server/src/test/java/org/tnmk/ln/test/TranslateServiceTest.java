package org.tnmk.ln.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.ln.infrastructure.translate.cache.Translation;
import org.tnmk.ln.infrastructure.translate.yandex.YandexTranslationService;

/**
 * @author khoi.tran on 2/1/17.
 */
public class TranslateServiceTest extends IntegrationBaseTest {
    @Autowired
    private YandexTranslationService translateService;

    @Test
    public void test() {
        Translation translation = translateService.translate("en", "vi", "hello World!");
        System.out.println(translation.getTranslatedText());
    }
}
