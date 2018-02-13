package org.tnmk.ln.test.factory;

import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.dictionary.entity.LexicalType;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/6/17.
 */
public class ExpressionTestFactory {
    public static ExpressionComposite constructWord(String text) {
        ExpressionComposite expression = new ExpressionComposite();
        expression.setText(text);
        expression.setSenseGroups(Arrays.asList(
                SenseGroupTestFactory.constructSenseGroup(LexicalType.NOUN)
                , SenseGroupTestFactory.constructSenseGroup(LexicalType.ADJ)
        ));
        return expression;
    }
}
