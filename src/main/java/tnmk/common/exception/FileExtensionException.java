package tnmk.common.exception;

import tnmk.common.exception.constant.ExceptionConstants;

/**
 * Use for wrong fileType.
 * Example: upload file which are not accepted: *.exe, *.bat...
 */
public class FileExtensionException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.File.FileType;
    private static final long serialVersionUID = 4902493607442088744L;

    public FileExtensionException(final String message) {
        super(ERROR_CODE, message);
    }

    public FileExtensionException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }

}
