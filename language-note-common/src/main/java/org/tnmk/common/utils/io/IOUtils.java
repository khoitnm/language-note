package org.tnmk.common.utils.io;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.exception.FileIOException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author khoi.tran on 7/26/16.
 */
public final class IOUtils {
    public static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {}

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static String loadTextFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (final IOException e) {
            final String msg = String.format("Cannot load String from '%s'", path);
            throw new FileIOException(msg, e);
        }
    }

    public static File createParentFolderIfNecessary(String filePath) {
        File destinationFile = new File(filePath);

        String parentPath = destinationFile.getParent();
        File parentFolder = new File(parentPath);
        try {
            if (!parentFolder.exists()) {
                FileUtils.forceMkdir(parentFolder);
            }
        } catch (IOException e) {
            throw new FileIOException(String.format("Cannot create folder '%s'", parentFolder.getAbsolutePath()), e);
        }
        return parentFolder;
    }

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static byte[] loadBinaryFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (final IOException e) {
            final String msg = String.format("Cannot load bytes from '%s'", path);
            throw new FileIOException(msg, e);
        }
    }

    private static InputStream validateExistInputStreamFromClassPath(final String path) {
        final InputStream inputStream = loadInputStreamFileInClassPath(path);
        if (inputStream == null) {
            throw new FileIOException(String.format("Not found file from '%s'", path));
        }
        return inputStream;
    }

    /**
     * @param path view more in {@link #loadBinaryFileInClassPath(String)}
     * @return
     */
    public static InputStream loadInputStreamFileInClassPath(String path) {
        return IOUtils.class.getResourceAsStream(path);
    }
}
