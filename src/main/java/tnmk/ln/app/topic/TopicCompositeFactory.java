package tnmk.ln.app.topic;

import tnmk.common.util.SetUtil;
import tnmk.ln.app.aggregation.model.TopicComposite;
import tnmk.ln.app.dictionary.ExpressionFactory;
import tnmk.ln.app.topic.entity.Topic;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class TopicCompositeFactory {
    public static TopicComposite constructSchema() {
        TopicComposite topic = new TopicComposite();
        topic.setExpressions(Arrays.asList(ExpressionFactory.constructSchema()));
        topic.setCategories(SetUtil.constructSet(CategoryFactory.constructSchema()));
        return topic;
    }

}
