package org.tnmk.ln.app.dictionary;

import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.dictionary.entity.ExpressionType;
import org.tnmk.ln.app.dictionary.entity.LexicalEntry;
import org.tnmk.ln.app.dictionary.entity.RelatedExpression;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class ExpressionFactory {
    public static ExpressionComposite constructSchema() {
        ExpressionComposite expression = new ExpressionComposite();
        expression.setLexicalEntries(Arrays.asList(new LexicalEntry()));
        expression.setExpressionType(ExpressionType.WORD);
        expression.setSenseGroups(Arrays.asList(SenseGroupFactory.constructSchema()));

        //Don't add Expression.constructSchema() here. Otherwise, it will cause endless-loop.
        expression.setAntonyms(Arrays.asList(new RelatedExpression()));
        expression.setSynonyms(Arrays.asList(new RelatedExpression()));
        expression.setFamily(Arrays.asList(new RelatedExpression()));
        return expression;
    }
}
