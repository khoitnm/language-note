package org.tnmk.common.util.http;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.http.MimeTypeUtils;

/**
 * @author khoi.tran on 6/5/17.
 */
public class MimeTypeUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(MimeTypeUtilsTest.class);

    @Test
    public void convert() {
        Assert.assertEquals("pdf",MimeTypeUtils.getFileExtensionFromMimeType(MimeTypeUtils.MIME_TYPE_PDF).toLowerCase());
        Assert.assertEquals("xls",MimeTypeUtils.getFileExtensionFromMimeType(MimeTypeUtils.MIME_TYPE_XLS).toLowerCase());
        Assert.assertEquals("xlsx",MimeTypeUtils.getFileExtensionFromMimeType(MimeTypeUtils.MIME_TYPE_XLSX).toLowerCase());

        Assert.assertNull(MimeTypeUtils.getFileExtensionFromMimeType(null));

        Assert.assertEquals(MimeTypeUtils.MIME_TYPE_PDF.toLowerCase(),MimeTypeUtils.getMimeTypeFromFileExtension("pdf").toLowerCase());
    }
}
