package org.tnmk.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.LogUtils;

/**
 * @author khoi.tran on 6/7/17.
 */
public class LogUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogUtilsTest.class);

    @Test
    public void test_toString() {
        String msg = LogUtils.toString(PersonFactory.createJasonBourne());
        LOGGER.debug(msg);
        Assert.assertNotNull(msg);

        msg = LogUtils.toStringMultiLine(PersonFactory.createJasonBourne());
        LOGGER.debug(msg);
        Assert.assertNotNull(msg);

        msg = LogUtils.toStringMultiLineForEachElement(PersonFactory.createPersons("a","b"));
        LOGGER.debug(msg);
        Assert.assertNotNull(msg);
    }

}
