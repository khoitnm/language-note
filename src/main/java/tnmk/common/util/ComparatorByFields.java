package tnmk.common.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author khoi.tran on 4/28/17.
 */
public class ComparatorByFields<T> implements Comparator<T> {
    private final String[] fields;
    private final EvaluationContext evaluationContext;
    private final List<Expression> fieldExpressions;

    public ComparatorByFields(String[] fields) {
        this.fields = fields;
        this.evaluationContext = ExpressionUtil.getContext();
        this.fieldExpressions = new ArrayList<>();
        for (String field : fields) {
            Expression fieldExpression = ExpressionUtil.getExpression(field);
            this.fieldExpressions.add(fieldExpression);
        }
    }

    @Override
    public int compare(T objectA, T objectB) {
        if (objectA == null && objectB == null) return 0;
        Integer result = ComparatorUtil.compareIfNull(objectA, objectB, 1);
        if (result != null) return result;

        for (Expression fieldExpression : fieldExpressions) {
            Object valA = fieldExpression.getValue(evaluationContext, objectA);
            Object valB = fieldExpression.getValue(evaluationContext, objectB);
            result = ComparatorUtil.compareNullable(valA, valB, 1);
            if (result != 0) {
                break;
            }
        }
        return result;
    }
}
