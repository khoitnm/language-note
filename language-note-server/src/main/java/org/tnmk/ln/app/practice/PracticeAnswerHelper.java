package org.tnmk.ln.app.practice;

import org.tnmk.ln.app.practice.entity.result.BasePracticeResult;

import java.util.List;

/**
 * @author khoi.tran on 4/30/17.
 */
public class PracticeAnswerHelper {
    public static final int LATEST_POINTS = 2;

    public static void calculateAnswerPoints(BasePracticeResult practiceResult) {
        practiceResult.setSumLatestAnswerPoint(totalAnswerPoints(practiceResult.getAnswers(), LATEST_POINTS) + practiceResult.getAdditionalPoints());
        practiceResult.setSumTotalAnswerPoint(totalAnswerPoints(practiceResult.getAnswers(), practiceResult.getAnswers().size()) + practiceResult.getAdditionalPoints());
    }

    public static double totalAnswerPoints(List<Float> answerPoints, int numPoints) {
        double result = 0;
        int startIndex = Math.max(0, answerPoints.size() - numPoints);
        for (int i = startIndex; i < answerPoints.size(); i++) {
            Float point = answerPoints.get(i);
            if (point != null) {
                result += point;
            }//else, consider point is 0
        }
        return result;
    }
}
