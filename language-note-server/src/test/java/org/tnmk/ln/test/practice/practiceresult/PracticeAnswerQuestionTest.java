package org.tnmk.ln.test.practice.practiceresult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.common.util.IterableUtil;
import org.tnmk.ln.app.aggregation.practice.QuestionRecommendationService;
import org.tnmk.ln.app.practice.QuestionRepository;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.app.practice.entity.result.AnswerResult;
import org.tnmk.ln.test.BaseTest;
import org.tnmk.ln.test.factory.CategoryTestFactory;
import org.tnmk.common.util.ObjectMapperUtil;
import org.tnmk.ln.app.practice.PracticeAnswerService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class PracticeAnswerQuestionTest extends BaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    CategoryTestFactory categoryTestFactory;
    @Autowired
    QuestionRecommendationService questionRecommendationService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    PracticeAnswerService practiceAnswerService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void answerQuestions() {
        Iterable<Question> questions = questionRepository.findAll(new PageRequest(0, 1), 3);
        Question question = IterableUtil.getFirst(questions);
        User owner = defaultUser;
        float point = RandomUtils.nextFloat(0, 1f);
        AnswerResult answerResult = practiceAnswerService.answerResult(owner, question.getId(), point);
        LOGGER.info("ExpressionPracticeResult: \n" + ObjectMapperUtil.toJson(new ObjectMapper(), answerResult));
        Assert.assertTrue(answerResult.getExpressionPracticeResult().getSumLatestAnswerPoint() > 0);
        Assert.assertTrue(answerResult.getQuestionPracticeResult().getSumLatestAnswerPoint() > 0);

    }

}
