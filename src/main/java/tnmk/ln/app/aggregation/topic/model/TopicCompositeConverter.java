package tnmk.ln.app.aggregation.topic.model;

import org.springframework.stereotype.Component;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.basemodel.BaseConverter;

import java.util.List;

/**
 * @author khoi.tran on 4/13/17.
 */
@Component
public class TopicCompositeConverter extends BaseConverter<Topic, TopicComposite> {
    public TopicComposite toTopicComposite(Topic topic, List<Expression> expressions) {
        TopicComposite topicComposite = super.toModel(topic);
        topicComposite.setExpressions(expressions);
        return topicComposite;
    }
}
