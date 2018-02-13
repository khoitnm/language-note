package org.tnmk.ln.test.factory;

import org.tnmk.ln.app.practice.entity.result.AnswerPoint;

/**
 * @author khoi.tran on 3/13/17.
 */
public class AnswerPointFactoryTest {
    public static AnswerPoint construct(int correctPoints, int totalPoints) {
        AnswerPoint answerPoint = new AnswerPoint();
        answerPoint.setCorrectPoints(correctPoints);
        answerPoint.setMaxPoints(totalPoints);
        return answerPoint;
    }
}
