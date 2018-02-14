package org.tnmk.ln.infrastructure.security.resourceserver.error;

/**
 * @deprecated Should use {@link org.tnmk.common.exception.model.error.ErrorResult} instead.
 */
@Deprecated
public class Result {
    private String requestPath;
    private String code;
    private String message;
    private Object details;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }
}
