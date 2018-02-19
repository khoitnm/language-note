package org.tnmk.common.utils;

/**
 * @author khoi.tran on 4/28/17.
 */
public class ComparatorUtils {
    /**
     * If one of them is null, return compare result. Otherwise, return null;
     *
     * @param valA
     * @param valB
     * @param nullHigher
     * @return
     */
    public static Integer compareIfNull(Object valA, Object valB, int nullHigher) {
        if (valA == null) {
            if (valB == null) {
                return 0;
            } else {
                return nullHigher;
            }
        } else {
            if (valB == null) {
                return -nullHigher;
            } else {
                return null;
            }
        }
    }

    public static int compareNullable(Object valA, Object valB, int nullHigher) {
        Integer result = compareIfNull(valA, valB, nullHigher);
        if (result == null) {
            return org.apache.commons.collections4.ComparatorUtils.NATURAL_COMPARATOR.compare(valA, valB);
        } else {
            return result;
        }
    }
}