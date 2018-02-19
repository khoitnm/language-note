package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * Use for wrong fileType.
 * Example: upload file which are not accepted: *.exe, *.bat...
 */
public class FileNotFoundException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.File.FileNotFound;
    private static final long serialVersionUID = 4902493607442088744L;

    public FileNotFoundException(final String message) {
        super(ERROR_CODE, message);
    }

    public FileNotFoundException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }

}
