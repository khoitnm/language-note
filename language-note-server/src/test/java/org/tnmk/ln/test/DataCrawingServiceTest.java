package org.tnmk.ln.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tnmk.ln.MainApplication;
import org.tnmk.ln.app.vocabulary.entity.Lesson;
import org.tnmk.ln.infrastructure.dataimport.phrasalverb.EnglishClubImport;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khoi.tran on 1/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("dev")
@WebAppConfiguration
public class DataCrawingServiceTest {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DataCrawingServiceTest.class);

    @Autowired
    private EnglishClubImport englishClubImport;

    @Test
    public void test_create_lesson() throws ParserConfigurationException {
        Lesson lesson = englishClubImport.loadWebsiteHtml();
        logger.debug("" + lesson);
    }

}
