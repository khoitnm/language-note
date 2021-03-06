package org.tnmk.ln.app.vocabulary.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.vocabulary.entity.Topic;
import org.tnmk.ln.app.vocabulary.repository.TopicMongoRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 1/26/17.
 */
@Service("vocabularyTopicService")
public class TopicService {

    @Autowired
    private TopicMongoRepository topicMongoRepository;

    public List<Topic> findAll() {
        return topicMongoRepository.findAll();
    }

    public Set<Topic> saveTopics(Set<Topic> topics) {
        for (Topic topic : topics) {
            cleanUpTopicName(topic);
        }
        Set<Topic> notBlankTopics = topics.stream().filter(topic -> StringUtils.isNotBlank(topic.getName())).collect(Collectors.toSet());
        Set<Topic> existingTopics = new HashSet<>();
        Set<Topic> newTopics = new HashSet<>();
        for (Topic itopic : notBlankTopics) {
            Topic existingTopic = topicMongoRepository.findOneByName(itopic.getName());
            if (existingTopic != null) {
                existingTopics.add(existingTopic);
            } else {
                newTopics.add(itopic);
            }
        }
        Set<Topic> result = topicMongoRepository.save(newTopics).stream().collect(Collectors.toSet());
        result.addAll(existingTopics);
        return result;
    }

    private void cleanUpTopicName(Topic topic) {
        topic.setName(cleanUpTopicName(topic.getName()));
    }

    private String cleanUpTopicName(String originalName) {
        String cleanedName = StringUtils.trim(originalName);
        if (StringUtils.isNotBlank(cleanedName)) {
            cleanedName = cleanedName.toLowerCase();
        }
        return cleanedName;
    }
}
