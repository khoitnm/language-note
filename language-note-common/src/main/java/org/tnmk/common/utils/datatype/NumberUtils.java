package org.tnmk.common.utils.datatype;

import org.apache.commons.lang3.StringUtils;

/**
 * Version 1.0.1
 */
public final class NumberUtils {
    private NumberUtils() {
        //Utils
    }

    public static Long toLongOrNull(Object num) {
        if (num == null) {
            return null;
        }
        Long result;
        try {
            result = toLong(num);
        } catch (NumberFormatException ex) {
            result = null;
        }
        return result;
    }
    public static Double toDoubleOrNull(Object num) {
        if (num == null) {
            return null;
        }
        if (num instanceof Double) {
            return (Double) num;
        } else if (num instanceof Number) {
            return ((Number) num).doubleValue();
        } else {
            Double result;
            try {
                result = Double.valueOf(num.toString());
            } catch (NumberFormatException ex) {
                result = null;
            }
            return result;
        }
    }
    public static Integer toIntegerOrNull(Object num) {
        if (num == null) {
            return null;
        }
        Integer result;
        try {
            result = toInteger(num);
        } catch (NumberFormatException ex) {
            result = null;
        }
        return result;
    }

    public static long toLong(Object num) {
        if (num instanceof Long) {
            return (Long) num;
        } else if (num instanceof Number) {
            return ((Number) num).longValue();
        } else {
            Double doubleNum = Double.valueOf(num.toString());
            return doubleNum.longValue();
        }
    }

    public static short toShort(Object num) {
        if (num instanceof Short) {
            return (Short) num;
        } else if (num instanceof Number) {
            return ((Number) num).shortValue();
        } else {
            Double doubleNum = Double.valueOf(num.toString());
            return doubleNum.shortValue();
        }
    }

    public static int toInteger(Object num) {
        if (num instanceof Integer) {
            return (Integer) num;
        } else if (num instanceof Number) {
            return ((Number) num).intValue();
        } else {
            Double doubleNum = Double.valueOf(num.toString());
            return doubleNum.intValue();
        }
    }
}
