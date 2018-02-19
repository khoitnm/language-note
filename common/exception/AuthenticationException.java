package org.tnmk.common.exception;

import org.tnmk.common.exception.constant.ExceptionConstants;

/**
 * @author dat.dang (2016-May-18)
 */
public class AuthenticationException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.Security.AuthenticationInvalid;
    private static final long serialVersionUID = -5046622369228818254L;

    public AuthenticationException(final String message) {
        super(ERROR_CODE, message);
    }

    public AuthenticationException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
