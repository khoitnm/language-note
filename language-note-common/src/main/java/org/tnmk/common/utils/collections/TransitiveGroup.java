package org.tnmk.common.utils.collections;

import java.util.List;

/**
 * the relationships between expressions which are transitive.
 * For example: A is a synonym of B, B is a synonym of C => A is a synonym of C.
 * <p>
 * However, "antonyms" is not a transitive relationship.
 * For example: A is an antonym of B, B is an antonym of C cannot conclude that A is an antonym of C.
 *
 * @author khoi.tran on 4/28/17.
 */
public class TransitiveGroup<E> {
    private List<E> items;

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }
}
