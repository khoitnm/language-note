package org.tnmk.ln.app.aggregation.practice.model;

import org.tnmk.ln.app.practice.entity.question.Question;

/**
 * @author khoi.tran on 4/19/17.
 */
public class QuestionComposite extends Question {
    private ExpressionComposite fromExpression;

    public ExpressionComposite getFromExpression() {
        return fromExpression;
    }

    public void setFromExpression(ExpressionComposite fromExpression) {
        this.fromExpression = fromExpression;
    }
}
