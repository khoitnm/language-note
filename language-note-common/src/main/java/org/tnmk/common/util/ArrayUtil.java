package org.tnmk.common.util;

import java.lang.reflect.Array;
import java.util.Collection;

public final class ArrayUtil {
    private ArrayUtil() {
    }

    public static boolean isEmpty(Object... objects) {
        return objects == null || objects.length == 0;
    }

    public static <T> T[] toArray(Collection<T> list) {
        if (list.isEmpty()) return (T[]) new Object[0];
        Class<T> componentType = (Class<T>) list.getClass().getComponentType();
        T[] array = (T[]) Array.newInstance(componentType, list.size());
        return list.toArray(array);
    }
}
