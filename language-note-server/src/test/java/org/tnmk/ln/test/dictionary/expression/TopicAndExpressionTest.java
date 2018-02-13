package org.tnmk.ln.test.dictionary.expression;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.test.factory.ExpressionTestFactory;
import org.tnmk.ln.test.factory.TopicTestFactory;
import org.tnmk.ln.app.aggregation.topic.TopicCompositeService;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;
import org.tnmk.ln.test.BaseTest;
import org.tnmk.ln.test.factory.CategoryTestFactory;
import org.tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class TopicAndExpressionTest extends BaseTest {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicCompositeService topicCompositeService;

    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory categoryTestFactory;

    @Autowired
    TopicTestFactory topicTestFactory;

    private static final User USER = UserTestFactory.constructUser(1l);

    @Test
    public void createTopic() {
        TopicComposite topicComposite = new TopicComposite();
        Topic topic = topicCompositeService.saveTopicAndRelations(USER, topicComposite);
        Assert.assertEquals(Topic.TITLE_DEFAULT, topic.getTitle());
    }

    @Test
    public void addExpressionToTopic() {
        User user = userTestFactory.initDefaultUser();
        Topic topic = topicTestFactory.initTopic(user, "test_" + System.currentTimeMillis(), "category" + System.currentTimeMillis(), "category" + System.currentTimeMillis());
        Expression expression = ExpressionTestFactory.constructWord("expression_" + System.currentTimeMillis());
//        topicCompositeService.addExpressionToTopic(user, topic.getId(), expression);
    }
}
