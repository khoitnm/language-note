package tnmk.ln.app.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author khoi.tran on 3/5/17.
 */
@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicAndOwnerRepository topicAndOwnerRepository;

    @Autowired
    private Neo4jRepository neo4jRepository;

    @Transactional
    public Topic saveIfNecessaryByTextAndOwner(User user, Topic topic) {
        //TODO use merge query of neo4j to improve performance?
        Topic result;
        topic.setOwner(user);
        if (topic.getId() != null) {
            result = topicRepository.save(topic);
        } else {
            Topic existingTopic = topicAndOwnerRepository.findOneByTextAndOwner(topic.getText(), user.getId());
            if (existingTopic == null) {
                topic.setOwner(user);
                result = topicRepository.save(topic);
            } else {
                result = existingTopic;
            }
        }
        return result;
    }

    //TODO can improve performance (using batch or Merge query?)
    @Transactional
    public Set<Topic> saveIfNecessaryByTextAndOwner(User user, Set<Topic> topics) {
        Set<Topic> result = new HashSet<>();
        for (Topic topic : topics) {
            Topic savedTopic = saveIfNecessaryByTextAndOwner(user, topic);
            result.add(savedTopic);
        }
        return result;
    }
}
