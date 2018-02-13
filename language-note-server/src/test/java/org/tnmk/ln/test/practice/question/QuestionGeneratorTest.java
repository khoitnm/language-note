package org.tnmk.ln.test.practice.question;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tnmk.ln.app.practice.QuestionFillBlankGenerator;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.app.practice.entity.question.QuestionPart;
import org.tnmk.ln.test.BaseTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionGeneratorTest extends BaseTest {
    @Autowired
    QuestionFillBlankGenerator questionFillBlankGenerator;

    @Test
    public void test() {
        String text = "Once upon a time there were 2 twin brothers who were not alike at all. Their names where Tim, and Tom. The twin brothers lived right across a candy shop.";
        List<String> expressions = Arrays.asList("once", "be", ".");
        for (String expression : expressions) {
            List<QuestionPart> questionPartList = questionFillBlankGenerator.analyzeToQuestionParts(Locale.EN_EN.getLanguage(), expression, text);
            LOGGER.debug("Question parts '{}':\n{}", expression, questionPartList);
        }
    }
}
