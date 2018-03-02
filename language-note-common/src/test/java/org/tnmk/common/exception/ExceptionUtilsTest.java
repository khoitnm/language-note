package org.tnmk.common.exception;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.exception.util.ExceptionUtils;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ExceptionUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtilsTest.class);

    @Test
    public void success_getExceptionMessage() {
        try {
            throw new NullPointerException("Testing exception");
        } catch (final Exception ex) {
            final String rootMessage = ExceptionUtils.getNullPointerExceptionRoot(ex);
            LOGGER.debug(rootMessage);
            Assert.assertNotNull(rootMessage);
        }
    }
}
