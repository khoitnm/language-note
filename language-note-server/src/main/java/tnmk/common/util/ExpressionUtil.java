package tnmk.common.util;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author khoi.tran on 4/25/17.
 */
public class ExpressionUtil {
    private static ExpressionParser loadParser() {
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        return new SpelExpressionParser(config);
    }

    private static Expression getExpression(ExpressionParser parser, String expressionString) {
        return parser.parseExpression(expressionString);
    }

    public static EvaluationContext getContext() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.addPropertyAccessor(new MapAccessor());
        context.addPropertyAccessor(new ReflectivePropertyAccessor());
        return context;
    }

    public static Expression getExpression(String expressionString) {
        ExpressionParser expressionParser = loadParser();
        return getExpression(expressionParser, expressionString);
    }

    public static Object getValue(Object root, String expressionString) {
        EvaluationContext evaluationContext = getContext();
        Expression expression = getExpression(expressionString);
        Object result = expression.getValue(evaluationContext, root);
        return result;
    }

    public static void setValue(Object root, String expressionString, Object value) {
        EvaluationContext evaluationContext = getContext();
        Expression expression = getExpression(expressionString);
        expression.setValue(evaluationContext, root, value);
    }
}
