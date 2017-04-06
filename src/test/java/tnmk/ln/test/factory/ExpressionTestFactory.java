package tnmk.ln.test.factory;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.LexicalType;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/6/17.
 */
public class ExpressionTestFactory {
    public static Expression constructWord(String text) {
        Expression expression = new Expression();
        expression.setText(text);
        expression.setSenseGroups(Arrays.asList(
                SenseGroupTestFactory.constructSenseGroup(LexicalType.NOUN)
                , SenseGroupTestFactory.constructSenseGroup(LexicalType.ADJ)
        ));
        return expression;
    }
}
