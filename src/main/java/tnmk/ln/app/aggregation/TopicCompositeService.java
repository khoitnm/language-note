package tnmk.ln.app.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.ExpressionRepository;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.QuestionGenerationService;
import tnmk.ln.app.topic.CategoryService;
import tnmk.ln.app.topic.TopicDetailRepository;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class TopicCompositeService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ExpressionRepository expressionRepository;

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

    @Transactional
    public Expression saveExpressionAndRelations(User user, Expression expression) {
        expression.setOwner(user);
        Expression savedExpression = expressionRepository.save(expression);
        questionService.createQuestionsIfNotExist(savedExpression);
        return savedExpression;
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

}
