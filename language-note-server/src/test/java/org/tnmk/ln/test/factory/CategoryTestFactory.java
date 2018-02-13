package org.tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.topic.entity.Category;
import org.tnmk.ln.app.topic.CategoryRepository;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class CategoryTestFactory {
    @Autowired
    private CategoryRepository categoryRepository;

    public static Category construct(String categoryText) {
        Category category = new Category();
        category.setText(categoryText);
        return category;
    }

    @Transactional
    public Category initCategory(String categoryText) {
        Category category = categoryRepository.findOneByText(categoryText);
        if (category == null) {
            category = construct(categoryText);
            category = categoryRepository.save(category);
        }
        return category;
    }
}
