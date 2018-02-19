package org.tnmk.common.utils.io;

import org.apache.commons.io.FilenameUtils;

/**
 * version: 1.0.0
 * @author khoi.tran on 4/28/17.
 */
public class FileUtils {
    public static String getFileExtension(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }
}
