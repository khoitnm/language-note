package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * Use for wrong fileSize.
 */
public class JsonConverterException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.General.JsonConvertionError;
    /**
     *
     */
    private static final long serialVersionUID = 2429969213914233228L;

    public JsonConverterException(final String message) {
        super(ERROR_CODE, message);
    }

    public JsonConverterException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }

    public JsonConverterException(final String message, final Object detailObject, final Throwable throwable) {
        super(ERROR_CODE, message, detailObject, throwable);
    }

}
