package org.tnmk.common.util.io;

import org.tnmk.common.exception.FileIOException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.io.IOUtils;

import java.io.InputStream;

/**
 * @author khoi.tran on 6/7/17.
 */
public class IOUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IOUtilsTest.class);

    private final String testFileLocation = "/testdata/person-jason-bourne.json";

    @Test
    public void success_loadtext() {
        final String text = IOUtils.loadTextFileInClassPath(testFileLocation);
        Assert.assertNotNull(text);
    }

    @Test
    public void success_loadInputStream() {
        final InputStream inputStream = IOUtils.loadInputStreamFileInClassPath(testFileLocation);
        Assert.assertNotNull(inputStream);
    }

    @Test
    public void success_loadBytes() {
        final byte[] bytes = IOUtils.loadBinaryFileInClassPath(testFileLocation);
        Assert.assertNotNull(bytes);
    }

    @Test(expected = FileIOException.class)
    public void exception_loadNotExistFile() {
        IOUtils.loadBinaryFileInClassPath("file_" + System.currentTimeMillis());
    }
}
