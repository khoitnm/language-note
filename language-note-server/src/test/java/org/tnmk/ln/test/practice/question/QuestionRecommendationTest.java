package org.tnmk.ln.test.practice.question;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.aggregation.practice.QuestionRecommendationService;
import org.tnmk.ln.test.IntegrationBaseTest;
import org.tnmk.ln.test.factory.CategoryTestFactory;
import org.tnmk.ln.test.factory.UserTestFactory;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class QuestionRecommendationTest extends IntegrationBaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory topicTestFactory;
    @Autowired
    QuestionRecommendationService questionRecommendationService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.getDefaultUser();
    }

    @Test
    public void question() {
//        long topicId = 1070;
        User owner = defaultUser;
//        List<QuestionWithPracticeResult> questions = questionRecommendationService.loadQuestionsByTopics(owner.getId(), QuestionType.EXPRESSION_RECALL);
//        LOGGER.info("Questions: \n" + ObjectMapperUtils.toJson(new ObjectMapper(), questions));
//        Assert.assertTrue(questions.size() > 3);
    }

}
