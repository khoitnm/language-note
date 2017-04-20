package tnmk.ln.app.aggregation.practice.model;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.entity.question.Question;

/**
 * @author khoi.tran on 4/19/17.
 */
public class QuestionComposite extends Question {
    private Expression fromExpression;

    public Expression getFromExpression() {
        return fromExpression;
    }

    public void setFromExpression(Expression fromExpression) {
        this.fromExpression = fromExpression;
    }
}
