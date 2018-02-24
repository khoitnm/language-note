package org.tnmk.common.utils.collections;

import org.springframework.util.CollectionUtils;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IterableUtils {
    private IterableUtils() {
    }

    /**
     * @deprecated You can use {@link org.apache.commons.collections4.IterableUtils#size(Iterable)}
     * @param iterable
     * @return
     */
    @Deprecated
    public static Integer count(Iterable<?> iterable) {
        if (iterable == null) {
            return null;
        }
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).size();
        }
        int count = 0;
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * @deprecated Use {@link org.apache.commons.collections4.IterableUtils#toList(Iterable)}
     * @param iterable
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> List<T> toList(Iterable<T> iterable) {
        if (iterable instanceof List) {
            return (List<T>) iterable;
        } else {
            List<T> result = new ArrayList<>();
            for (T t : iterable) {
                result.add(t);
            }
            return result;
        }
    }

    public static <T> T getFirst(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public static <T> void removeFirstItems(Iterable<T> iterable, int removedItems) {
        Iterator<T> iterator = iterable.iterator();
        int i = 0;
        while (iterator.hasNext() && i < removedItems) {
            iterator.next();
            iterator.remove();
            i++;
        }
    }

    @Deprecated
    public static <T> Set<T> toSet(Iterable<T> iterable) {
        if (iterable instanceof Set) {
            return (Set<T>) iterable;
        } else {
            Set<T> result = new HashSet<>();
            for (T t : iterable) {
                result.add(t);
            }
            return result;
        }
    }

    public static <T> boolean containsByField(Iterable<T> items, T findingItem, String byPropertyName) {
        T foundItem = findItemHasSameFieldValue(items, findingItem, byPropertyName);
        return (foundItem != null);
    }

    public static <T> T findItemHasSameFieldValue(Iterable<T> items, T findingItem, String byPropertyName) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T iItem = iterator.next();
            if (iItem == null) continue;
            if (isEqualsByFieldValueExcludeNull(iItem, findingItem, byPropertyName)) {
                return iItem;
            }
        }
        return null;
    }

    public static <T> void removeItemsByFieldValue(Iterable<T> items, T findingItem, String byPropertyName) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T iItem = iterator.next();
            if (iItem == null) continue;
            if (isEqualsByFieldValueExcludeNull(iItem, findingItem, byPropertyName)) {
                iterator.remove();
            }
        }
    }

    private static boolean isEqualsByFieldValueIncludingNull(Object a, Object b, String byPropertyName) {
        Object iItemPropertyValue = ReflectionUtils.readProperty(a, byPropertyName);
        Object findingItemPropertyValue = ReflectionUtils.readProperty(b, byPropertyName);
        if (iItemPropertyValue == null) {
            if (findingItemPropertyValue == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (findingItemPropertyValue == null) {
                return false;
            } else {
                return iItemPropertyValue.equals(findingItemPropertyValue);
            }
        }
    }

    private static boolean isEqualsByFieldValueExcludeNull(Object a, Object b, String byPropertyName) {
        Object iItemPropertyValue = ReflectionUtils.readProperty(a, byPropertyName);
        Object findingItemPropertyValue = ReflectionUtils.readProperty(b, byPropertyName);
        if (iItemPropertyValue == null || findingItemPropertyValue == null) {
            return false;
        } else {
            return iItemPropertyValue.equals(findingItemPropertyValue);
        }
    }

    /**
     * Find items exist in B but not in A
     *
     * @param itemAs
     * @param itemBs
     * @param byPropertyName
     * @return
     */
    public static <T> List<T> findItemsNotInFirstListByField(List<T> itemAs, List<T> itemBs, String byPropertyName) {
        List<T> addedItems = new ArrayList<>();
        if (CollectionUtils.isEmpty(itemAs)) {
            if (!CollectionUtils.isEmpty(itemBs)) {
                addedItems.addAll(itemBs);
            }
        } else {
            for (T itemB : itemBs) {
                if (!containsByField(itemAs, itemB, byPropertyName)) {
                    addedItems.add(itemB);
                }
            }
        }
        return addedItems;
    }
}
