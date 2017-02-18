package tnmk.common.exception;

import tnmk.common.exception.constant.ExceptionConstants;

@SuppressWarnings("serial")
public class BadArgumentException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.General.BadArgument;

    /**
     * Constructor for InvalidArgumentException.
     *
     * @param msg the detail message
     */
    public BadArgumentException(final String msg) {
        super(ERROR_CODE, msg);
    }

    public BadArgumentException(final String msg, final Throwable throwable) {
        super(ERROR_CODE, msg, throwable);
    }
}
