package tnmk.ln.app.aggregation.practice;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.PracticeFavouriteService;
import tnmk.ln.infrastructure.basemodel.BaseConverter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 4/30/17.
 */
@Component
public class ExpressionCompositeConverter extends BaseConverter<Expression, ExpressionComposite> {
    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    public ExpressionComposite toExpressionComposite(Long userId, Expression expression) {
        if (expression == null) return null;
        ExpressionComposite expressionComposite = new ExpressionComposite();
        BeanUtils.copyProperties(expression, expressionComposite);
        int favourite = practiceFavouriteService.findExpressionFavourite(userId, expression.getId());
        expressionComposite.setFavourite(favourite);
        return expressionComposite;
    }

    public List<ExpressionComposite> toExpressionComposites(Long userId, List<Expression> expressions) {
        List<ExpressionComposite> result = expressions.stream().map(expression -> toExpressionComposite(userId, expression)).collect(Collectors.toList());
        return result;
    }
}
