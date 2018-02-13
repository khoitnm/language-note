package tnmk.common.infrastructure.validator;

import org.springframework.stereotype.Component;
import tnmk.common.exception.BeanValidationException;
import tnmk.common.exception.UnexpectedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author khoi.tran
 */
@Component
public class BeanValidator {

    private Validator validator;

    public BeanValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * This method will validate base on annotations in the bean (e.g. @NotNull, @NotEmpty, @Email...).
     *
     * @param target the object which will be validated. If the target is null, we don't need to validate it, there's no Exception will be thrown.
     *               This behaviour is the same as javax.validation.Validator (don't validate with null properties).
     * @throws BeanValidationException if there's any wrong field value, it will throw a {@link BeanValidationException} object.
     */
    public void validate(Object target) {
        if (target != null) {
            Set<ConstraintViolation<Object>> violations = validator.validate(target);
            if (!violations.isEmpty()) {
                throw new BeanValidationException("Error in BeanValidator", target, violations);
            }
        }
    }

    public <T extends RuntimeException> void validate(Object target, Class<T> exceptionClass) {
        if (target != null) {
            Set<ConstraintViolation<Object>> violations = validator.validate(target);
            if (!violations.isEmpty()) {
                try {
                    Constructor<T> exceptionConstructor = exceptionClass.getConstructor(String.class, Object.class, Set.class);
                    T ex = exceptionConstructor.newInstance("Error in BeanValidator", target, violations);
                    throw ex;
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new UnexpectedException(String.format("The exception class must has a constructor (String, Object, Set). Your input Exception: %s", exceptionClass.getName()));
                }
            }
        }
    }
}
