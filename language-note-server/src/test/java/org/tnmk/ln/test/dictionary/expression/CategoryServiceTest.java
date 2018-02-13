package org.tnmk.ln.test.dictionary.expression;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.topic.CategoryAndOwnerRepository;
import org.tnmk.ln.app.topic.CategoryService;
import org.tnmk.ln.app.topic.entity.Category;
import org.tnmk.ln.test.BaseTest;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.app.topic.CategoryRepository;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;
import org.tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class CategoryServiceTest extends BaseTest {
    @Autowired
    TopicService topicService;

    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryAndOwnerRepository categoryCountRepository;
    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void saveCategoryIfNotExist() {
        String text = "category_test";
        User owner = defaultUser;

        Category category = new Category();
        category.setText(text);
        category.setOwner(defaultUser);
        Category categorySaved01 = categoryService.saveIfNecessaryByTextAndOwner(defaultUser, category);
        long categorysCountBefore = categoryCountRepository.countByTextAndOwner(text, owner.getId());

        category.setId(null);
        category.setCreatedDateTime(null);
        Category categorySaved02 = categoryService.saveIfNecessaryByTextAndOwner(defaultUser, category);

        long categorysCountAfter = categoryCountRepository.countByTextAndOwner(text, owner.getId());
        LOGGER.info("Count: {} -> {}", categorysCountBefore, categorysCountAfter);

        Assert.assertEquals(categorySaved01.getId(), categorySaved02.getId());
        Assert.assertEquals(categorysCountBefore, categorysCountAfter);
    }

}
