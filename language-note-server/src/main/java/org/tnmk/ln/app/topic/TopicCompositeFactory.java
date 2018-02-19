package org.tnmk.ln.app.topic;

import org.tnmk.common.utils.collections.SetUtils;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.dictionary.ExpressionFactory;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class TopicCompositeFactory {
    public static TopicComposite constructSchema() {
        TopicComposite topic = new TopicComposite();
        topic.setExpressions(Arrays.asList(ExpressionFactory.constructSchema()));
        topic.setCategories(SetUtils.constructSet(CategoryFactory.constructSchema()));
        return topic;
    }

}
