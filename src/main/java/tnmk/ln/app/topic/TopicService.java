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

    @Transactional
    public Topic saveTopicAndRelations(User user, Topic topic) {
        topic.setOwner(user);
        Set<Expression> expressionSet = topic.getExpressions();
        categoryService.saveIfNecessaryByTextAndOwner(user, topic.getCategories());
        if (expressionSet != null) {
            expressionSet.stream().forEach(expression -> expression.setOwner(user));
        }
        topic = topicRepository.save(topic);
        if (expressionSet != null) {
            expressionSet.stream().forEach(expression -> questionService.createQuestionsIfNotExist(expression));
        }
        return topic;
    }

    public Topic findOneById(Long topicId) {
        return topicRepository.findOne(topicId, 2);
    }

    public List<Topic> findByOwnerId(User user) {
        return topicDetailRepository.findByOwner(user.getId());
    }

    public List<Topic> findAll() {
        return IterableUtil.toList(topicRepository.findAll());
    }

    /**
     * @param expressionEditor
     * @param topicId
     * @param expression       if expression is new, it will be sent. Otherwise, it will be updated.
     */
    @Transactional
    public void addExpressionToTopic(User expressionEditor, Long topicId, Expression expression) {
        boolean isNewExpression = expression.getId() == null;
        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setExpressions(SetUtil.constructSet(expression));
        expression.setOwner(expressionEditor);
        topicRepository.save(topic);
        if (isNewExpression) {
            questionService.createQuestions(expression);
        }
    }

    public Topic findOneByTitle(Long userId, String title) {
        return topicDetailRepository.findOneByTitleAndOwner(userId, title);
    }

    public Topic findDetailById(Long topicId) {
        return topicDetailRepository.findOneDetailById(topicId);
    }

    public List<Topic> getTopicBriefsByOwner(User user) {
        List<Topic> topics = topicDetailRepository.findByOwner(user.getId(), 2);
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
}
