package org.tnmk.common.utils.collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.reflection.expression.ComparatorByFields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author khoi.tran (2015-Jul-17)
 */
public final class ListUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(ListUtils.class);

    private ListUtils() {
    }

    /**
     * Note: below code is copied from http://stackoverflow.com/questions/7348054/how-to-subtract-collections-with-comparator-interface-instead-of-overriding-equa.
     *
     * @param source     the original items.
     * @param removeList the items which will be removed from original items.
     * @param comparator the comparator which is used to compare which items is removed.
     * @return remain items in original after removing.
     */
    public static <T> List<T> removeAll(Collection<T> source, Collection<T> removeList, Comparator<T> comparator) {
        Set<T> removeSet = new TreeSet<T>(comparator);
        removeSet.addAll(removeList);
        List<T> result = new ArrayList<T>();
        for (T item : source) {
            if (!removeSet.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static <T> void addToListWithMaxSize(Collection<T> list, T newItem, int maxSize) {
        list.add(newItem);
        int removeItems = list.size() - maxSize;
        if (removeItems > 0) {
            IterableUtils.removeFirstItems(list, removeItems);
        }
    }

    public static <T> void sortByFields(List<T> list, String... fields) {
        ComparatorByFields<T> comparatorByFields = new ComparatorByFields(fields);
        Collections.sort(list, comparatorByFields);
    }

    public static <E> List<E> getTop(List<E> source, int topSize) {
        List<E> result;
        if (topSize <= source.size()) {
            result = source.subList(0, topSize);
        } else {
            result = new ArrayList<>(source);
        }
        return result;
    }
}
