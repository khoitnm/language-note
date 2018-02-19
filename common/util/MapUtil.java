package org.tnmk.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khoi.tran on 8/17/16.
 */
public class MapUtil {

    /**
     * If field value is null, it will not be input into map
     *
     * @param list
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> Map<Object, T> mapListByFieldName(List<T> list, String fieldName) {
        Map<Object, T> map = new HashMap<>();
        for (T item : list) {
            Object key = ReflectionUtils.readProperty(item, fieldName);
            if (key != null) {
                map.put(key, item);
            }
        }
        return map;
    }
}
