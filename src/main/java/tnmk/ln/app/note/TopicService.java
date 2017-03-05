package tnmk.ln.app.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.IterableUtil;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

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
    public Topic saveIfNecessaryByTextAndOwner(Topic topic) {
        //TODO use merge query of neo4j
        Topic existingTopic = topicAndOwnerRepository.findOneByTextAndOwner(topic.getText(), topic.getOwner().getId());
        if (existingTopic == null) {
            return topicRepository.save(topic);
        } else {
            return existingTopic;
        }
    }

    @Transactional
    public Set<Topic> saveIfNecessaryByTextAndOwner(Set<Topic> topics) {
        return IterableUtil.toSet(topicRepository.save(topics));
    }
}
