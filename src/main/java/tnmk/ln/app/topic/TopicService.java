package tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.QuestionGenerationService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicDetailRepository topicDetailRepository;

    @Autowired
    private QuestionGenerationService questionService;

    @Autowired
    private CategoryService categoryService;

    public Topic findOneById(Long topicId) {
        return topicRepository.findOne(topicId, 2);
    }

    public List<Topic> findByOwnerId(User user) {
        return topicDetailRepository.findByOwner(user.getId());
    }

    public List<Topic> findAll() {
        return IterableUtil.toList(topicRepository.findAll());
    }

    public Topic findOneByTitle(Long userId, String title) {
        return topicDetailRepository.findOneByTitleAndOwner(userId, title);
    }

    public Topic findDetailById(Long topicId) {
        //TODO must avoid cycling relationship.
        return topicDetailRepository.findOneDetailById(topicId);
    }

    public List<Topic> getTopicBriefsByOwner(User user) {
        List<Topic> topics = topicDetailRepository.findByOwner(user.getId(), 1);
//        topics.stream().forEach(topic -> limitExpressionInTopics(topic, 4));
        return topics;
    }

    private void limitExpressionInTopics(Topic topic, int maxExpressions) {
        Set<Expression> expressions = SetUtil.getTop(topic.getExpressions(), maxExpressions);
        topic.setExpressions(expressions);
    }

    @Transactional
    public void rename(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic saveTopic(User user, Topic topic) {
        topic.setOwner(user);
        return topicRepository.save(topic, 2);
    }
}
