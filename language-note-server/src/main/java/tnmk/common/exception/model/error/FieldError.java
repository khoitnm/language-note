package tnmk.common.exception.model.error;

import java.io.Serializable;

public class FieldError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String objectName;

    private String field;

    private String message;

    public FieldError() {
        //This method is necessary for Json converter or Serializable.
    }

    public FieldError(String code, String objectName, String field, String message) {
        this.code = code;
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public String getField() {
        return this.field;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }
}
