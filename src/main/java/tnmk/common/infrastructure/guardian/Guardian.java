package tnmk.common.infrastructure.guardian;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnmk.common.exception.UnexpectedException;

/**
 * @author khoi.tran on 3/11/17.
 */
public class Guardian {
    public static final Logger LOGGER = LoggerFactory.getLogger(Guardian.class);

    public static void validateNotNull(Object object, String message) {
        String msg = StringUtils.isNotBlank(message) ? message : "Expect a not null object";
        if (object == null) throw new UnexpectedException(msg);
    }

    public static void validateArrayNotEmpty(Object array, String message) {
        boolean isArrayHasValue = (array != null) && array.getClass().isArray() ?
                java.lang.reflect.Array.getLength(array) > 0 :
                false;
        if (!isArrayHasValue) {
            String msg = StringUtils.isNotBlank(message) ? message : "The array must be not null and not empty";
            throw new UnexpectedException(msg);
        }

    }
}
