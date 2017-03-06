package tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.note.TopicRepository;
import tnmk.ln.app.note.entity.Topic;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class TopicTestFactory {
    @Autowired
    private TopicRepository topicRepository;

    public static Topic construct(String topicText) {
        Topic topic = new Topic();
        topic.setText(topicText);
        return topic;
    }

    @Transactional
    public Topic initTopic(String topicText) {
        Topic topic = topicRepository.findOneByText(topicText);
        if (topic == null) {
            topic = construct(topicText);
            topic = topicRepository.save(topic);
        }
        return topic;
    }
}
