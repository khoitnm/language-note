package org.tnmk.ln.test;

import org.junit.Assert;
import org.junit.Test;
import org.tnmk.ln.app.vocabulary.util.UserPointUtils;
import org.tnmk.ln.app.vocabulary.entity.AnswerCalculation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author khoi.tran on 1/31/17.
 */
public class UserPointUtilsTest extends UnitBaseTest {
    @Test
    public void test_Calculation_null_or_empty() {
        AnswerCalculation answerCalculation = UserPointUtils.calculateCorrectAnswersForLastElements(Collections.emptyList(), 5);
        Assert.assertEquals(0, answerCalculation.getTotalCount());
        Assert.assertEquals(0, answerCalculation.getCorrectCount());
        Assert.assertEquals(0, answerCalculation.getCorrectPoint());
        Assert.assertEquals(0, answerCalculation.getCorrectPercentage(), 0.01);

        AnswerCalculation answerCalculation2 = UserPointUtils.calculateCorrectAnswersForLastElements(null, 5);
        Assert.assertEquals(0, answerCalculation2.getTotalCount());
        Assert.assertEquals(0, answerCalculation2.getCorrectCount());
        Assert.assertEquals(0, answerCalculation2.getCorrectPoint());
        Assert.assertEquals(0, answerCalculation2.getCorrectPercentage(), 0.01);
    }

    @Test
    public void test_Calculation_less_than_list() {
        List<Integer> answers = Arrays.asList(2, 3, 0, 1);
        AnswerCalculation answerCalculation = UserPointUtils.calculateCorrectAnswersForLastElements(answers, 3);
        Assert.assertEquals(3, answerCalculation.getTotalCount());
        Assert.assertEquals(2, answerCalculation.getCorrectCount());
        Assert.assertEquals(4, answerCalculation.getCorrectPoint());
        Assert.assertEquals((2 / (double) 3), answerCalculation.getCorrectPercentage(), 0.01);
    }

    @Test
    public void test_Calculation_more_than_list() {
        List<Integer> answers = Arrays.asList(2, 3, 0, 1);
        AnswerCalculation answerCalculation2 = UserPointUtils.calculateCorrectAnswersForLastElements(answers, 5);
        Assert.assertEquals(4, answerCalculation2.getTotalCount());
        Assert.assertEquals(3, answerCalculation2.getCorrectCount());
        Assert.assertEquals(6, answerCalculation2.getCorrectPoint());
        Assert.assertEquals((3 / (double) 4), answerCalculation2.getCorrectPercentage(), 0.01);
    }

    @Test
    public void test_Calculation_all() {
        List<Integer> answers = Arrays.asList(2, 3, 0, 1);
        AnswerCalculation answerCalculation2 = UserPointUtils.calculateCorrectAnswersForLastElements(answers, -1);
        Assert.assertEquals(4, answerCalculation2.getTotalCount());
        Assert.assertEquals(3, answerCalculation2.getCorrectCount());
        Assert.assertEquals(6, answerCalculation2.getCorrectPoint());
        Assert.assertEquals((3 / (double) 4), answerCalculation2.getCorrectPercentage(), 0.01);
    }
}
