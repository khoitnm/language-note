package tnmk.ln.app.note;

import tnmk.ln.app.note.entity.Topic;

/**
 * @author khoi.tran on 3/4/17.
 */
public class TopicFactory {
    public static Topic constructSchema() {
        Topic topic = new Topic();
        return topic;
    }
}
