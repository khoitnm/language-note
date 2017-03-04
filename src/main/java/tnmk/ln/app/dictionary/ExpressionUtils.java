package tnmk.ln.app.dictionary;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
public class ExpressionUtils {
    public static Set<Sense> getSenses(Expression expression) {
        return expression.getSensesGroups().stream().flatMap(senseGroup -> senseGroup.getSenses().stream()).collect(Collectors.toSet());
    }


}
