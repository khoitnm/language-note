package org.tnmk.common.utils;

import java.util.Comparator;

/**
 * @author khoi.tran on 4/28/17.
 */
public final class ComparatorUtils {
    private ComparatorUtils() {
        //Utils
    }

    /**
     * If one of them is null, return compare result. Otherwise, return null;<br/>
     * <bold>Note:</bold> You may want to consider using {@link java.util.Comparator#nullsFirst(Comparator)}.
     *
     * @param valA
     * @param valB
     * @param nullHigher how much do you want a not-null value is highe a null value.
     * @return null if both valA and valB are not null. return 0 if both valA and valB are null.
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
            //This method cannot work with null objects.
            return org.apache.commons.collections4.ComparatorUtils.NATURAL_COMPARATOR.compare(valA, valB);
        } else {
            return result;
        }
    }
}
