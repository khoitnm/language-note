package tnmk.common.util;

public final class ArrayUtil {
    private ArrayUtil() {
    }

    public static boolean isEmpty(Object... objects) {
        return objects == null || objects.length == 0;
    }
}
