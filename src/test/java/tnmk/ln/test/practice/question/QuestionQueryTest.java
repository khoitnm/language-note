package tnmk.ln.test.practice.question;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tnmk.ln.app.practice.QuestionLoadingRepository;
import tnmk.ln.app.practice.QuestionRepository;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionType;
import tnmk.ln.test.BaseTest;

/**
 * @author khoi.tran on 3/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionQueryTest extends BaseTest {
    @Autowired
    QuestionLoadingRepository questionLoadingRepository;

    @Autowired
    QuestionRepository questionRepository;

    /**
     * MATCH (n:`Question`) WHERE n.`questionType` = 'EXPRESSION_RECALL' MATCH (m0:`Expression`) WHERE id(m0) = 1103 MATCH (m1:`Sense`) WHERE id(m1) = 1113 MATCH (n)-[:`FROM_EXPRESSION`]->(m0) MATCH (n)-[:`FROM_SENSE`]->(m1) WITH n MATCH p=(n)-[*0..1]-(m) RETURN p, ID(n)
     */
    @Test
    public void test() {
//        long questionId = 1156;
//        long expressionId = 1103;
//        long senseId = 1113;
//        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseId(QuestionType.EXPRESSION_RECALL, expressionId, senseId);

        long questionId = 1164;
        long expressionId = 1103;
        long senseId = 1113;
        long exampleId = 1084;
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType.FILL_BLANK, expressionId, senseId, exampleId);
        Assert.assertEquals(questionId, question.getId(), 0.01);
    }
}
