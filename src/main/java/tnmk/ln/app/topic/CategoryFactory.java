package tnmk.ln.app.topic;

import tnmk.ln.app.topic.entity.Category;

/**
 * @author khoi.tran on 3/4/17.
 */
public class CategoryFactory {
    public static Category constructSchema() {
        Category category = new Category();
        return category;
    }
}
