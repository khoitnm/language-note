package tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;
import tnmk.ln.app.aggregation.topic.model.TopicComposite;
import tnmk.ln.app.aggregation.topic.model.TopicCompositeConverter;
import tnmk.ln.app.dictionary.ExpressionRepository;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.QuestionGenerationService;
import tnmk.ln.app.topic.CategoryService;
import tnmk.ln.app.topic.TopicDetailRepository;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class TopicCompositeService {

    @Autowired
    private TopicCompositeConverter topicCompositeConverter;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ExpressionService expressionService;

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
    public Topic saveTopicAndRelations(User user, TopicComposite topicComposite) {
        topicComposite.setOwner(user);
        List<Expression> expressions = topicComposite.getExpressions();

        categoryService.saveIfNecessaryByTextAndOwner(user, topicComposite.getCategories());

        if (expressions != null) {
            expressions.stream().forEach(expression -> expression.setOwner(user));
        }
        expressions = expressionRepository.save(expressions);
        topicComposite.setExpressionIds(expressions.stream().map(iexpression -> iexpression.getId()).collect(Collectors.toList()));

        Topic topic = topicCompositeConverter.toEntity(topicComposite);
        topic = topicRepository.save(topic);
        topicComposite = topicCompositeConverter.toTopicComposite(topic, expressions);

        if (expressions != null) {
            expressions.stream().forEach(expression -> questionService.createQuestionsIfNotExist(expression));
        }
        return topicComposite;
    }

    @Transactional
    public Expression saveExpressionAndRelations(User user, Expression expression) {
        expression.setOwner(user);
        Expression savedExpression = expressionRepository.save(expression);
        questionService.createQuestionsIfNotExist(savedExpression);
        return savedExpression;
    }

    public TopicComposite findDetailById(String topicId) {
        Topic topic = topicService.findDetailById(topicId);
        List<String> expressionIds = topic.getExpressionIds();
        List<Expression> expressions = !ListUtils.isEmpty(expressionIds) ? expressionService.findByIds(expressionIds) : new ArrayList<>();
        TopicComposite topicComposite = topicCompositeConverter.toTopicComposite(topic, expressions);
        return topicComposite;
    }

//    /**
//     * @param expressionEditor
//     * @param topicId
//     * @param expression       if expression is new, it will be sent. Otherwise, it will be updated.
//     */
//    @Transactional
//    public void addExpressionToTopic(User expressionEditor, Long topicId, Expression expression) {
//        boolean isNewExpression = expression.getId() == null;
//        Topic topic = new Topic();
//        topic.setId(topicId);
//        topic.setExpressions(SetUtil.constructSet(expression));
//        expression.setOwner(expressionEditor);
//        topicRepository.save(topic);
//        if (isNewExpression) {
//            questionService.createQuestions(expression);
//        }
//    }

}
