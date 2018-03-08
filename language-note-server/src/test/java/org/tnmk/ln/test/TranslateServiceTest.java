package org.tnmk.ln.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tnmk.ln.MainApplication;
import org.tnmk.ln.infrastructure.translate.cache.Translation;
import org.tnmk.ln.infrastructure.translate.yandex.YandexTranslationService;

/**
 * @author khoi.tran on 2/1/17.
 */
public class TranslateServiceTest extends BaseTest{
    @Autowired
    private YandexTranslationService translateService;

    @Test
    public void test() {
        Translation translation = translateService.translate("en", "vi", "hello World!");
        System.out.println(translation.getTranslatedText());
    }
}
