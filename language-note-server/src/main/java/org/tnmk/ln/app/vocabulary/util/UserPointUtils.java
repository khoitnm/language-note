package org.tnmk.ln.app.vocabulary.util;

import org.tnmk.ln.app.vocabulary.entity.AnswerCalculation;

import java.util.List;

/**
 * @author khoi.tran on 1/31/17.
 */
public class UserPointUtils {

    /**
     * @param answers
     * @param lastElements if you want to calculate all, use -1.
     * @return
     */
    public static AnswerCalculation calculateCorrectAnswersForLastElements(List<Integer> answers, int lastElements) {
        AnswerCalculation result = new AnswerCalculation();

        int totalCount = 0;
        int correctCount = 0;
        int correctPoint = 0;
        double percentage = 0;
        if (answers == null || answers.isEmpty()) {
            return result;
        }

        int beginIndex = lastElements < 0 ? 0 : Math.max(0, answers.size() - lastElements);
        for (int i = beginIndex; i < answers.size(); i++) {
            totalCount++;
            int ianswer = answers.get(i);
            if (ianswer > 0) {
                correctCount++;
                correctPoint += ianswer;
            }
        }
        if (totalCount > 0) {
            percentage = correctCount / (double) totalCount;
        }
        result.setCorrectCount(correctCount);
        result.setCorrectPoint(correctPoint);
        result.setCorrectPercentage(percentage);
        result.setTotalCount(totalCount);
        return result;
    }
}
