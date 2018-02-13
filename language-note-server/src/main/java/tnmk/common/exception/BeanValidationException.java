package tnmk.common.exception;

import tnmk.common.exception.constant.ExceptionConstants;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

/**
 * @author khoi.tran
 */
public class BeanValidationException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.General.BeanValidationInValid;
    private static final long serialVersionUID = -5046622369228818254L;
    private final Object errorTarget;

    private final Set<ConstraintViolation<Object>> violations;

    /**
     * @param message     general error message.
     * @param errorTarget the object which contains invalid properties.
     * @param violations  the validation result.
     */
    public BeanValidationException(final String message, final Object errorTarget, final Set<ConstraintViolation<Object>> violations) {
        super(ERROR_CODE, message);
        this.errorTarget = errorTarget;
        this.violations = Collections.unmodifiableSet(violations);
    }

    public Object getErrorTarget() {
        return this.errorTarget;
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return this.violations;
    }
}
