package org.tnmk.common.utils.collections;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static boolean isEmpty(Object... objects) {
        return objects == null || objects.length == 0;
    }
}
