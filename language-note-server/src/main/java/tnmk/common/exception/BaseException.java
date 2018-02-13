package tnmk.common.exception;

/**
 * This is the base exception of this project.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -8279292782665815881L;
    /**
     * At this moment (2016-04-19), the error code is number, but in future it can be string (e.g. "12.01.02").
     * That's why I use string here so that we can change errorCode easily.
     */
    private final String errorCode;

    private final String errorMessage;

    private final String detailsType;
    private final transient Object details;

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = null;
        this.detailsType = null;
    }

    /**
     * @param errorCode   view in {@link tnmk.common.exception.constant.ExceptionConstants}#ERROR_CODE_XXX
     * @param message
     * @param details
     * @param detailsType view in {@link tnmk.common.exception.constant.ExceptionConstants}#DETAILS_TYPE_XXX
     */
    public BaseException(String errorCode, String message, Object details, String detailsType) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = details;
        this.detailsType = detailsType;
    }

    public BaseException(String errorCode, String message, Object details) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = details;
        this.detailsType = null;
    }

    public BaseException(String errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = throwable.getMessage();
        this.details = null;
        this.detailsType = null;
    }

    public BaseException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = null;
        this.detailsType = null;
    }

    public BaseException(String errorCode, String message, Object details, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = details;
        this.detailsType = null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getDetails() {
        return this.details;
    }

    public String getDetailsType() {
        return detailsType;
    }
}
