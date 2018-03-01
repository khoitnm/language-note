package org.tnmk.common.util.io;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.exception.FileIOException;
import org.tnmk.common.utils.io.FileUtils;
import org.tnmk.common.utils.io.IOUtils;

import java.io.InputStream;

/**
 * @author khoi.tran on 6/7/17.
 */
public class FileUtilsTest {

    @Test
    public void getFileExtension() {
        Assert.assertEquals("a2b",FileUtils.getFileExtension("C:/xxx/yyy.zzz.t-z.a2b"));
    }
}
