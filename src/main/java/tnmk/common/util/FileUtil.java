package tnmk.common.util;

import org.apache.commons.io.FilenameUtils;

/**
 * @author khoi.tran on 4/28/17.
 */
public class FileUtil {
    public static String getFileExtension(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }
}
