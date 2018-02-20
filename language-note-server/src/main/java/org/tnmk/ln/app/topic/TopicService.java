package org.tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.common.utils.collections.IterableUtils;
import org.tnmk.common.utils.datatype.NumberUtils;
import org.tnmk.ln.app.practice.QuestionGenerationService;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;

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

    public Topic findOneById(String topicId) {
        return topicRepository.findOne(NumberUtils.toLong(topicId));
    }

    public List<Topic> findByOwnerId(User user) {
        return topicDetailRepository.findByOwner(user.getId());
    }

    public List<Topic> findAll() {
        return IterableUtils.toList(topicRepository.findAll());
    }

    /**
     * @param userId
     * @param title
     * @return This method returns only topic with exactly name (case-sensitive)
     */
    public Topic findOneByTitle(Long userId, String title) {
        return topicDetailRepository.findOneByTitleAndOwner(userId, title);
    }

    /**
     *
     * @param userId
     * @param title
     * @return The topics with same name (case-insensitive would be return)
     */
    public List<Topic> lookupByTitleAndOwner(Long userId, String title){
        return topicDetailRepository.lookupByTitleAndOwner(userId, title.trim());
    }

    public Topic findDetailById(String topicId) {
        return topicRepository.findOne(NumberUtils.toLong(topicId));
    }

//    private void limitExpressionInTopics(Topic topic, int maxExpressions) {
//        Set<Expression> expressions = SetUtils.getTop(topic.getExpressions(), maxExpressions);
//        topic.setExpressions(expressions);
//    }

    @Transactional
    public void rename(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic saveTopic(User user, Topic topic) {
        topic.setOwner(user);
        return topicRepository.save(topic);
    }
}
