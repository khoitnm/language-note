package org.tnmk.ln.app.aggregation.topic.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;
import org.tnmk.ln.app.aggregation.practice.ExpressionCompositeConverter;
import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.basemodel.BaseConverter;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 4/13/17.
 */
@Component
public class TopicCompositeConverter extends BaseConverter<Topic, TopicComposite> {
    @Autowired
    private ExpressionCompositeConverter expressionCompositeConverter;

    public TopicComposite toTopicComposite(User user, Topic topic, List<? extends Expression> expressions) {
        TopicComposite topicComposite = super.toModel(topic);
        if (ListUtils.isEmpty(expressions)) {
            topicComposite.setExpressions((List<ExpressionComposite>) expressions);
        } else {
//            Expression firstExpression = expressions.get(0);
//            if (firstExpression instanceof ExpressionComposite) {
//                topicComposite.setExpressions((List<ExpressionComposite>) expressions);
//            } else {
                //find favourite
                List<ExpressionComposite> expressionComposites = new ArrayList<>();
                for (Expression expression : expressions) {
                    expressionComposites.add(expressionCompositeConverter.toExpressionComposite(user.getId(), expression));
                }
                topicComposite.setExpressions(expressionComposites);
//            }
        }
        return topicComposite;
    }

}
