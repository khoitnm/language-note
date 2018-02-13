package org.tnmk.common.infrastructure.validator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author khoi.tran on 7/25/16.
 */
public class ElementNotBlankValidator implements ConstraintValidator<ElementNotBlank, List> {
    EmailValidator emailValidator = new EmailValidator();

    @Override
    public void initialize(ElementNotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(List values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return true;
        for (Object value : values) {
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                String str = (String) value;
                if (StringUtils.isBlank(str)) {
                    return false;
                }
            }
        }
        return true;
    }
}
