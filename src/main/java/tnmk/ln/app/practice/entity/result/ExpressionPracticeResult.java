package tnmk.ln.app.practice.entity.result;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.dictionary.entity.Expression;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = "ExpressionPracticeResult")
public class ExpressionPracticeResult extends BasePracticeResult {
    public static final String RESULT_OF_EXPRESSION = "RESULT_OF_EXPRESSION";

    @Relationship(type = RESULT_OF_EXPRESSION, direction = Relationship.OUTGOING)
    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
