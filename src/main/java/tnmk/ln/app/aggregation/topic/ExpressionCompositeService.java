package tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.aggregation.practice.ExpressionCompositeConverter;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 4/30/17.
 */
@Service
public class ExpressionCompositeService {
    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private ExpressionCompositeConverter expressionCompositeConverter;

    public ExpressionComposite findById(Long userId, String expressionId) {
        Expression expression = expressionService.findById(expressionId);
        return expressionCompositeConverter.toExpressionComposite(userId, expression);
    }
}
