package tnmk.ln.test.practice.practiceresult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.practice.PracticeAnswerService;
import tnmk.ln.app.practice.QuestionRecommendationService;
import tnmk.ln.app.practice.query.ExpressionPracticeResult;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.AnswerPointFactoryTest;
import tnmk.ln.test.factory.CategoryTestFactory;
import tnmk.ln.test.factory.QuestionFactoryTest;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class PracticeAnswerQuestionTest extends BaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory topicTestFactory;
    @Autowired
    QuestionRecommendationService questionRecommendationService;

    @Autowired
    PracticeAnswerService practiceAnswerService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void answerQuestions() {
        long topicId = 1070;
        long questionId = 1166;
        long expressionId = 1101;
        User owner = defaultUser;
        ExpressionPracticeResult expressionPracticeResult = practiceAnswerService.answerResult(owner, QuestionFactoryTest.constructExpressionRecallQuestion(questionId, expressionId), AnswerPointFactoryTest.construct(1, 1));
        LOGGER.info("ExpressionPracticeResult: \n" + ObjectMapperUtil.toJson(new ObjectMapper(), expressionPracticeResult));
        Assert.assertTrue(expressionPracticeResult.getLatestAnswerPoints() > 0);
    }

}
