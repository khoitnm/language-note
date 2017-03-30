package tnmk.ln.app.topic;

import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.ExpressionFactory;
import tnmk.ln.app.topic.entity.Topic;

/**
 * @author khoi.tran on 3/4/17.
 */
public class TopicFactory {
    public static Topic constructSchema() {
        Topic topic = new Topic();
        topic.setExpressions(SetUtil.constructSet(ExpressionFactory.constructSchema()));
        topic.setCategories(SetUtil.constructSet(CategoryFactory.constructSchema()));
        return topic;
    }

}
