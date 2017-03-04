package tnmk.ln.app.dictionary;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.ExpressionType;
import tnmk.ln.app.dictionary.entity.LexicalEntry;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class ExpressionFactory {
    public static Expression constructExpression() {
        Expression expression = new Expression();
        expression.setLexicalEntries(Arrays.asList(new LexicalEntry()));
        expression.setExpressionType(ExpressionType.WORD);
        expression.setSensesGroups(Arrays.asList(SenseGroupFactory.constructSenseGroup()));

        expression.setAntonyms(Arrays.asList(new Expression()));
        expression.setSynonyms(Arrays.asList(new Expression()));
        expression.setFamily(Arrays.asList(new Expression()));
        return expression;
    }
}
