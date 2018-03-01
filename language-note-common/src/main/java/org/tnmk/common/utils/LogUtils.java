package org.tnmk.common.utils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public final class LogUtils {
    private LogUtils(){
        //Utils
    }

    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        return ReflectionToStringBuilder.toString(object, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * This method is usually for logging only.
     *
     * @param object
     * @return
     */
    public static String toStringMultiLine(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * This method is usually for logging only.
     *
     * @param list
     * @return
     */
    public static String toStringMultiLineForEachElement(List<?> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder("[\n");
        for (Object element : list) {
            result.append(toStringMultiLine(element));
            result.append(", \n");
        }
        result.append("\n]");
        return result.toString();
    }
}
