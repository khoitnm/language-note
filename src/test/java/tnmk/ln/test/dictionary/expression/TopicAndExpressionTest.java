package tnmk.ln.test.dictionary.expression;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.CategoryTestFactory;
import tnmk.ln.test.factory.ExpressionTestFactory;
import tnmk.ln.test.factory.TopicTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class TopicAndExpressionTest extends BaseTest {
    @Autowired
    TopicService topicService;

    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory categoryTestFactory;

    @Autowired
    TopicTestFactory topicTestFactory;

    private static final User USER = UserTestFactory.constructUser(1l);

    @Test
    public void createTopic() {
        Topic topic = new Topic();
        topic = topicService.saveTopicAndRelationships(USER, topic);
        Assert.assertEquals(Topic.TITLE_DEFAULT, topic.getTitle());
    }

    @Test
    public void addExpressionToTopic() {
        User user = userTestFactory.initDefaultUser();
        Topic topic = topicTestFactory.initTopic(user, "test_" + System.currentTimeMillis(), "category" + System.currentTimeMillis(), "category" + System.currentTimeMillis());
        Expression expression = ExpressionTestFactory.constructWord("expression_" + System.currentTimeMillis());
        topicService.addExpressionToTopic(user, topic.getId(), expression);
    }
}
