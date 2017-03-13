package tnmk.ln.test.practice.question;

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
import tnmk.ln.app.practice.QuestionRecommendationService;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionType;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.NoteTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

import java.util.List;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class QuestionRecommendationTest extends BaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    NoteTestFactory noteTestFactory;
    @Autowired
    QuestionRecommendationService questionRecommendationService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void question() {
        long noteId = 1070;
        User owner = defaultUser;
        List<Question> questions = questionRecommendationService.loadQuestionsByNotes(owner.getId(), QuestionType.EXPRESSION_RECALL, noteId);
        LOGGER.info("Questions: \n" + ObjectMapperUtil.toJson(new ObjectMapper(), questions));
        Assert.assertTrue(questions.size() > 3);
    }

}
