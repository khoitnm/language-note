package org.tnmk.ln.app.dictionary;

import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.Sense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
public class ExpressionUtils {
    public static List<Sense> getSenses(Expression expression) {
        List<Sense> result = new ArrayList<>();
        if (expression.getSenseGroups() != null) {
            result = expression.getSenseGroups().stream().flatMap(senseGroup -> senseGroup.getSenses().stream()).collect(Collectors.toList());
        }
        return result;
    }

}
