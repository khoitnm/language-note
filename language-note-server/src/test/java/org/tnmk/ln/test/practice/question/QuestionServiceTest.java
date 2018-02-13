package org.tnmk.ln.test.practice.question;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.practice.QuestionService;
import org.tnmk.ln.app.topic.CategoryAndOwnerRepository;
import org.tnmk.ln.app.topic.CategoryRepository;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.test.BaseTest;
import org.tnmk.ln.test.factory.CategoryTestFactory;
import org.tnmk.ln.test.factory.UserTestFactory;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;

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
