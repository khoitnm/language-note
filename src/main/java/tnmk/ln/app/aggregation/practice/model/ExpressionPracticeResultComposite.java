package tnmk.ln.app.aggregation.practice.model;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;

/**
 * @author khoi.tran on 4/19/17.
 * @deprecated use QuestionComposite instead.
 */
@Deprecated
public class ExpressionPracticeResultComposite extends ExpressionPracticeResult {
    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
