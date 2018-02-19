package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * @author dat.dang (2016-May-18)
 */
public class FileTransferException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.File.FileTransfer;
    private static final long serialVersionUID = 4180557732581405174L;

    public FileTransferException(final String message) {
        super(ERROR_CODE, message);
    }

    public FileTransferException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
