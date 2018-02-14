package org.tnmk.ln.test.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.aggregation.topic.TopicCompositeService;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.topic.TopicCompositeFactory;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.app.topic.entity.Category;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class TopicTestFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryTestFactory.class);
    @Autowired
    private UserTestFactory userFactory;

    @Autowired
    private CategoryTestFactory categoryTestFactory;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicCompositeService topicCompositeService;

    @Autowired
    private TopicRepository topicRepository;

    public static TopicComposite constructTopic(String title, String... categoryTexts) {
        TopicComposite topic = TopicCompositeFactory.constructSchema();
        topic.setTitle(title);
        Set<Category> categorys = new HashSet<>();
        for (String categoryText : categoryTexts) {
            Category category = CategoryTestFactory.construct(categoryText);
            categorys.add(category);
        }
        topic.setCategories(categorys);
        return topic;
    }

    @Transactional
    public Topic initTopic(User owner, String title, String... categoryTexts) {
        Topic topic = topicService.findOneByTitle(owner.getId(), title);
        if (topic == null) {
            topic = new Topic();
            if (StringUtils.isNotBlank(title)) {
                topic.setTitle(title);
            }
        }
        Set<Category> categorys = new HashSet<>();
        for (String categoryText : categoryTexts) {
            Category category = categoryTestFactory.initCategory(categoryText);
            categorys.add(category);
        }
        topic.setCategories(categorys);
        topic.setOwner(owner);
        topic = topicRepository.save(topic);
        topic = topicService.findDetailById("" + topic.getId());
        return topic;
    }

    /**
     * @param owner
     * @param topicTitle
     * @return topic with 2 categorys and 3 expressions
     */
    public Topic createTopicWithDefaultRelationships(User owner, String topicTitle) {
        TopicComposite topicComposite = constructTopic(topicTitle, "test_category1", "test_category2");
        topicComposite.setExpressions(Arrays.asList(
                ExpressionTestFactory.constructWord("test_expression1")
                , ExpressionTestFactory.constructWord("test_expression2")
                , ExpressionTestFactory.constructWord("test_expression3")
        ));
        Topic topic = topicCompositeService.saveTopicAndRelations(owner, topicComposite);
        LOGGER.debug("Created topic: " + topicComposite);
        return topicComposite;
    }
}
