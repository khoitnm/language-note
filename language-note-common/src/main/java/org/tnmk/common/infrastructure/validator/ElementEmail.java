package org.tnmk.common.infrastructure.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author khoi.tran on 7/25/16.
 */
@Documented
@Constraint(validatedBy = ElementEmailValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementEmail {
    String message() default "The element[{0}] in the collections must be email";

    /**
     * Necessary for using with {@link Constraint}
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean ignoreCase() default false;
}