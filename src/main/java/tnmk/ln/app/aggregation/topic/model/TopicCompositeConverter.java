package tnmk.ln.app.aggregation.topic.model;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.PracticeFavouriteService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.basemodel.BaseConverter;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 4/13/17.
 */
@Component
public class TopicCompositeConverter extends BaseConverter<Topic, TopicComposite> {
    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    public TopicComposite toTopicComposite(User user, Topic topic, List<? extends Expression> expressions) {
        TopicComposite topicComposite = super.toModel(topic);
        if (ListUtils.isEmpty(expressions)) {
            topicComposite.setExpressions((List<ExpressionComposite>) expressions);
        } else {
            Expression firstExpression = expressions.get(0);
            if (firstExpression instanceof ExpressionComposite) {
                topicComposite.setExpressions((List<ExpressionComposite>) expressions);
            } else {
                //find favourite
                List<ExpressionComposite> expressionComposites = new ArrayList<>();
                for (Expression expression : expressions) {
                    expressionComposites.add(toExpressionComposite(user, expression));
                }
                topicComposite.setExpressions(expressionComposites);
            }
        }
        return topicComposite;
    }

    public ExpressionComposite toExpressionComposite(User user, Expression expression) {
        ExpressionComposite expressionComposite = new ExpressionComposite();
        BeanUtils.copyProperties(expression, expressionComposite);
        Integer favourite = practiceFavouriteService.findExpressionFavourite(user, expression.getId());
        expressionComposite.setFavourite(favourite);
        return expressionComposite;
    }
}
