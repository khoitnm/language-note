package tnmk.ln.app.aggregation.practice;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.PracticeFavouriteService;
import tnmk.ln.infrastructure.basemodel.BaseConverter;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 4/30/17.
 */
@Component
public class ExpressionCompositeConverter extends BaseConverter<Expression, ExpressionComposite> {
    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    public ExpressionComposite toExpressionComposite(Long userId, Expression expression) {
        ExpressionComposite expressionComposite = new ExpressionComposite();
        BeanUtils.copyProperties(expression, expressionComposite);
        int favourite = practiceFavouriteService.findExpressionFavourite(userId, expression.getId());
        expressionComposite.setFavourite(favourite);
        return expressionComposite;
    }
}
