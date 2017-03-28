package tnmk.ln.test.practice.question;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.practice.QuestionService;
import tnmk.ln.app.topic.CategoryAndOwnerRepository;
import tnmk.ln.app.topic.CategoryRepository;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.CategoryTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class QuestionServiceTest extends BaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory topicTestFactory;

    @Autowired
    TopicService topicService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CategoryAndOwnerRepository categoryCountRepository;
    @Autowired
    ExpressionService expressionService;

    @Autowired
    QuestionService questionService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void question() {
//        User owner = defaultUser;
//        String topicTitle = "test_topic_" + DateTimeUtil.formatLocalDateTimeForFilePath();
//        Topic topic = topicTestFactory.createTopicWithDefaultRelationships(owner, topicTitle);
//        TopicAssert.assertExpressions(topic, owner, 3);
//
//        List<Question> questions = questionService.loadQuestionsByTopics(owner, topic.getId());
//        LOGGER.info("Questions: \n" + ObjectMapperUtil.toStringMultiLineForEachElement(questions));
//        Assert.assertTrue(questions.size() > 3);
    }

}
