package org.tnmk.ln.test.practice.question;

import org.junit.Test;
import org.tnmk.ln.app.practice.QuestionFillBlankGenerator;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.app.practice.entity.question.QuestionPart;
import org.tnmk.ln.infrastructure.nlp.LemmaFindingService;
import org.tnmk.ln.test.UnitBaseTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/11/17.
 */
public class QuestionGeneratorTest extends UnitBaseTest {
    private QuestionFillBlankGenerator questionFillBlankGenerator = new QuestionFillBlankGenerator(new LemmaFindingService());

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
