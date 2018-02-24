package org.tnmk.common.utils.collections;

import org.tnmk.common.utils.reflection.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static boolean isEmpty(Object... objects) {
        return objects == null || objects.length == 0;
    }
}
