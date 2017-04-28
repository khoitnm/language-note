package tnmk.common.util;

import org.apache.commons.collections4.ComparatorUtils;

/**
 * @author khoi.tran on 4/28/17.
 */
public class ComparatorUtil {
    public static int compareNullable(Object valA, Object valB, int nullHigher) {
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
                return ComparatorUtils.NATURAL_COMPARATOR.compare(valA, valB);
            }
        }
    }
}
