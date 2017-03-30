package tnmk.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IterableUtil {
    private IterableUtil() {
    }

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
        if (iterator.hasNext() && i < removedItems) {
            iterator.remove();
            i++;
        }
    }

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
}
