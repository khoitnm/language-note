package tnmk.common.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SetUtil {
    public static <E> Set<E> constructSet(E... elements) {
        Set<E> result = new HashSet<>();
        for (E element : elements) {
            result.add(element);
        }
        return result;
    }

    public static <E> Set<E> getTop(Set<E> source, int topSize) {
        Set<E> result = new HashSet<>();
        int i = 0;
        for (E e : source) {
            if (i >= topSize) {
                break;
            }
            result.add(e);
            i++;
        }
        return result;
    }
}
