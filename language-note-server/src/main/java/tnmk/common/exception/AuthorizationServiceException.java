package tnmk.common.exception;

import tnmk.common.exception.constant.ExceptionConstants;

@SuppressWarnings("serial")
public class AuthorizationServiceException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.Security.AuthorizationInvalid;

    /**
     * Constructor for AuthorizationServiceException.
     *
     * @param msg the detail message
     */
    public AuthorizationServiceException(final String msg) {
        super(ERROR_CODE, msg);
    }
}
