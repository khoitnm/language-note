package org.tnmk.common.exception;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.tnmk.common.exception.util.ExceptionUtils;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ExceptionUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtilsTest.class);

    @Test
    public void success_getNullPointerExceptionMessage() {
        try {
            throw new NullPointerException();
        } catch (final Exception ex) {
            final String rootMessage = ExceptionUtils.getNullPointerExceptionRoot(ex);
            LOGGER.debug(rootMessage);
            String classCausedException = ExceptionUtilsTest.class.getSimpleName();
            Assert.assertTrue(rootMessage.contains(classCausedException));
        }
    }

    @Test
    public void success_getRootCauseException_NestedException() {
        String originalErrorMessage = "Testing SQL exception";
        try {
            Exception rootCauseException = new BeanCreationException(originalErrorMessage);
            Exception secondException = new BeanCreationException("Second Exception",rootCauseException);
            throw new BeanCreationException("Third message", secondException);
        } catch (final Exception ex) {
            final String rootMessage = ExceptionUtils.getExceptionRoot(ex);
            LOGGER.debug(rootMessage);
            Assert.assertTrue(rootMessage.contains(originalErrorMessage));
        }
    }

    @Test
    public void success_getRootCauseException_NoNestedException() {
        String originalErrorMessage = "Testing SQL exception";
        try {
            throw new BeanCreationException(originalErrorMessage);
        } catch (final Exception ex) {
            final String rootMessage = ExceptionUtils.getExceptionRoot(ex);
            LOGGER.debug(rootMessage);
            Assert.assertTrue(rootMessage.contains(originalErrorMessage));
        }
    }
}
