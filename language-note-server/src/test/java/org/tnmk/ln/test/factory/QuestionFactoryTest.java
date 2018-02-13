package org.tnmk.ln.test.factory;

import org.tnmk.ln.app.practice.entity.question.QuestionExpressionRecall;

/**
 * @author khoi.tran on 3/13/17.
 */
public class QuestionFactoryTest {
    public static QuestionExpressionRecall constructExpressionRecallQuestion(long id) {
        QuestionExpressionRecall questionExpressionRecall = new QuestionExpressionRecall();
        questionExpressionRecall.setId(id);
        return questionExpressionRecall;
    }

//    public static QuestionExpressionRecall constructExpressionRecallQuestion(long questionId, long expressionId) {
//        QuestionExpressionRecall questionExpressionRecall = new QuestionExpressionRecall();
//        questionExpressionRecall.setId(questionId);
//
//        Expression expression = new Expression();
//        expression.setId(expressionId);
//        questionExpressionRecall.setFromExpression(expression);
//        return questionExpressionRecall;
//
//    }
}
