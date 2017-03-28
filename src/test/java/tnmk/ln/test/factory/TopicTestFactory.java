package tnmk.ln.test.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.topic.TopicFactory;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.app.topic.entity.Category;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

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
    private TopicRepository topicRepository;

    public static Topic constructTopic(String title, String... categoryTexts) {
        Topic topic = TopicFactory.constructSchema();
        topic.setTitle(title);
        Set<Category> categorys = new HashSet<>();
        for (String categoryText : categoryTexts) {
            Category category = CategoryTestFactory.construct(categoryText);
            categorys.add(category);
        }
        topic.setCategorys(categorys);
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
        topic.setCategorys(categorys);
        topic.setOwner(owner);
        topic = topicRepository.save(topic);
        topic = topicService.findDetailById(topic.getId());
        return topic;
    }

    /**
     * @param owner
     * @param topicTitle
     * @return topic with 2 categorys and 3 expressions
     */
    public Topic createTopicWithDefaultRelationships(User owner, String topicTitle) {
        Topic topic = constructTopic(topicTitle, "test_category1", "test_category2");
        topic.setExpressions(SetUtil.constructSet(
                ExpressionTestFactory.constructWord("test_expression1")
                , ExpressionTestFactory.constructWord("test_expression2")
                , ExpressionTestFactory.constructWord("test_expression3")
        ));
        topic = topicService.saveTopicAndRelationships(owner, topic);
        LOGGER.debug("Created topic: " + topic);
        return topic;
    }
}
