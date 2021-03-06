package org.tnmk.ln.test;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tnmk.common.test.IntegrationTest;
import org.tnmk.ln.MainApplication;

/**
 * This base class is used for testing functions which need to be run in a Spring context.
 * <br/>
 * However, according to this <a href="https://www.sitepoint.com/javascript-testing-unit-functional-integration/">link</a>. I/O, DB, URL... testing should be integration test, not Unit test. So I should thinking about it.
 * @author khoi.tran on 2/26/17.
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@Ignore
public class IntegrationBaseTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IntegrationBaseTest.class);
}
