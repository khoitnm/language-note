package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * Use for wrong fileSize.
 */
public class FileSizeException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.File.FileSize;
    private static final long serialVersionUID = 4180557732581405174L;

    public FileSizeException(final String message) {
        super(ERROR_CODE, message);
    }

    public FileSizeException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }

}
