package tnmk.common.exception.model.error;

import java.util.List;

public class ErrorResult {
    private String code;
    private Object details;
    private Object detailsType;
    private String userMessage;
    private String developerMessage;
    private List<FieldError> fieldErrors;

    // Used by Jackson
    public ErrorResult() {
    }

    public ErrorResult(String code, String userMessage, String developerMessage) {
        this.code = code;
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public Object getDetailsType() {
        return detailsType;
    }

    public void setDetailsType(Object detailsType) {
        this.detailsType = detailsType;
    }
}
