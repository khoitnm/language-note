package org.tnmk.ln.infrastructure.data.neo4j.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author khoi.tran on 3/1/17.
 * @deprecated this annotation is used for update or delete cascade. However, it's too complicated to do that. It has no effected now.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Deprecated
public @interface CascadeRelationship {
    String value() default "";
}

