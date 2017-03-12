package tnmk.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnmk.common.exception.FileIOException;
import tnmk.common.exception.FileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author khoi.tran on 7/26/16.
 */
public class IOUtil {
    public static final Logger LOG = LoggerFactory.getLogger(IOUtil.class);

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static String loadTextFileInClassPath(String path) {
        try {
            InputStream inputStream = IOUtil.class.getResourceAsStream(path);
            if (inputStream == null) throw new FileNotFoundException(String.format("Cannot load String from '%s'", path));
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            String msg = String.format("Cannot load String from '%s'", path);
            throw new FileNotFoundException(msg, e);
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
    public static byte[] loadBinaryFileInClassPath(String path) {
        try {
            return IOUtils.toByteArray(IOUtil.class.getResourceAsStream(path));
        } catch (IOException e) {
            String msg = String.format("Cannot load String from '%s'", path);
            throw new FileNotFoundException(msg, e);
        }
    }

    /**
     * @param path view more in {@link #loadBinaryFileInClassPath(String)}
     * @return
     */
    public static InputStream loadInputStreamFileInClassPath(String path) {
        return IOUtil.class.getResourceAsStream(path);
    }
}
