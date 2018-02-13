package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * @author dat.dang (2016-May-18)
 */
public class FileIOException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.File.FileIOError;
    private static final long serialVersionUID = 4180557732581405174L;

    public FileIOException(final String message) {
        super(ERROR_CODE, message);
    }

    public FileIOException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
