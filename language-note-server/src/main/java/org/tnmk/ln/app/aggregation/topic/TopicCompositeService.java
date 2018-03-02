package org.tnmk.ln.app.aggregation.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.aggregation.topic.model.TopicCompositeConverter;
import org.tnmk.ln.app.practice.QuestionGenerationService;
import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.dictionary.ExpressionRepository;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.practice.PracticeFavouriteService;
import org.tnmk.ln.app.topic.CategoryService;
import org.tnmk.ln.app.topic.TopicDetailRepository;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class TopicCompositeService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TopicCompositeService.class);

    @Autowired
    private TopicCompositeConverter topicCompositeConverter;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ExpressionCompositeService expressionCompositeService;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private TopicDetailRepository topicDetailRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    @Autowired
    private QuestionGenerationService questionService;

    /**
     * <pre>
     *     Process of saving expressions inside a Topic:
     *      1.  When clicking on saving an Expression in a Topic, only save that Expression, not saving the whole Topic
     *      2.  When clicking on saving a Topic, just save the Topic as well as the reference ExpressionIds.
     *          If there are new Expressions (with empty Ids), save them first, then update their ids into the Topic.
     * </pre>
     *
     * @param user
     * @param topicComposite
     * @return
     */
    @Transactional
    public Topic saveTopicAndRelations(User user, TopicComposite topicComposite) {
        topicComposite.setOwner(user);
        List<ExpressionComposite> expressions = topicComposite.getExpressions();

        categoryService.saveIfNecessaryByTextAndOwner(user, topicComposite.getCategories());

        if (expressions != null) {
            expressions.stream().forEach(expression -> expressionCompositeService.prepareForSavingExpression(user, expression));
            expressions = expressionRepository.save(expressions);
            practiceFavouriteService.saveExpressionFavourites(user, expressions);
            List<String> expressionIds = expressions.stream().map(iexpression -> iexpression.getId()).collect(Collectors.toList());
            topicComposite.setExpressionIds(expressionIds);
        }

        Topic topic = topicCompositeConverter.toEntity(topicComposite);
        topic = topicRepository.save(topic);
        //If ExpressionComposite has favourite, save favourite
        topicComposite = topicCompositeConverter.toTopicComposite(user, topic, expressions);

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

    public TopicComposite findDetailById(User user, String topicId) {
        Topic topic = topicService.findDetailById(topicId);
        List<String> expressionIds = topic.getExpressionIds();
        List<Expression> expressions = !CollectionUtils.isEmpty(expressionIds) ? expressionService.findByIds(expressionIds) : new ArrayList<>();
        TopicComposite topicComposite = topicCompositeConverter.toTopicComposite(user, topic, expressions);
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
