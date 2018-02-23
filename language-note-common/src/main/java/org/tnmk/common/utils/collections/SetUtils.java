package org.tnmk.common.utils.collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SetUtils {
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


    /**
     * This is an example how to use this method:
     * <pre>
     * <code>
     * Stream&lt;Person&gt; persons = distinctListByProperty(persons, Person::getName);
     * </code>
     * </pre>
     * @param list
     * @param keyExtractor
     * @param <T>
     */
    public static <T> Stream<T> distinctListByProperty(List<T> list, Function<? super T, ?> keyExtractor){
        return list.stream().filter(distinctByPropertyFilter(keyExtractor));
    }

    private static <T> Predicate<T> distinctByPropertyFilter(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
