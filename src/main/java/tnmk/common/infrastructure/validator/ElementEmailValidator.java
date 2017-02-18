package tnmk.common.infrastructure.validator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author khoi.tran on 7/25/16.
 */
public class ElementEmailValidator implements ConstraintValidator<ElementEmail, List> {
    EmailValidator emailValidator = new EmailValidator();

    @Override
    public void initialize(ElementEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(List values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return true;

        for (Object value : values) {
            if (!isElementValid(value, context)) {
                return false;
            }
        }
        return true;
    }

    private boolean isElementValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            String str = (String) value;
            if (StringUtils.isBlank(str)) {
                return true;
            } else {
                if (!emailValidator.isValid(str, context)) {
                    return false;
                }
            }
        }
        return true;
    }
}
