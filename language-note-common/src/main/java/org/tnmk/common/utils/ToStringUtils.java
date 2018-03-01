package org.tnmk.common.utils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.util.List;

/**
 * Provides functionality for formatting object to string which usually used in Logging.
 * Basically, methods in this utils will never ever throw exception no matter what the input object.
 */
public final class ToStringUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(ToStringUtils.class);
    private static final int MAX_LOGGING_ELEMENTS_IN_AN_ARRAY = 7;

    private ToStringUtils() {
        //Utils
    }

    public static String toString(final Object object) {
        return toString(object, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * This method is usually for logging only.
     *
     * @param object
     * @return
     */
    public static String toStringMultiLine(final Object object) {
        return toString(object, ToStringStyle.MULTI_LINE_STYLE);
    }

    private static String toString(final Object object, final ToStringStyle toStringStyle) {
        try {
            if (object == null) {
                return null;
            } else if (object instanceof String) {
                return (String) object;
            } else if (ReflectionUtils.isSimpleType(object.getClass())) {
                return String.valueOf(object);
            }
            return ReflectionToStringBuilder.toString(object, toStringStyle);
        } catch (Exception ex) {
            LOGGER.warn("Cannot convert object to String: " + ex.getMessage(), ex);
            return String.valueOf(object);
        }
    }

    /**
     * This method is usually for logging only.
     *
     * @param list
     * @return
     */
    public static String toStringMultiLineForEachElement(final List<?> list) {
        if (list == null) {
            return null;
        }
        final StringBuilder result = new StringBuilder("[\n");
        for (final Object element : list) {
            result.append(toStringMultiLine(element));
            result.append(", \n");
        }
        result.append("\n]");
        return result.toString();
    }

    /**
     * This method is usually used for print the simple data (String, Number, Array, List, Map).
     * If you want to print a custom object, please consider using other methods.
     * @param object
     * @return
     */
    public static String toSimpleString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        } else if (object.getClass().isArray()) {
            return toStringOfArray((Object[]) object);
        } else {
            return String.valueOf(object);
        }
    }

    //TODO consider moving to {@link ToStringUtils}
    private static String toStringOfArray(Object[] arr) {
        StringBuilder result = new StringBuilder("[");
        int i = 0;
        for (Object element : arr) {
            if (result.length() > 1) {
                result.append(",");
            }
            if (i >= MAX_LOGGING_ELEMENTS_IN_AN_ARRAY) {
                result.append("...");
                break;
            }
            result.append(toSimpleString(element));
            i++;
        }
        result.append("]");
        return result.toString();
    }
}
