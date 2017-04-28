package tnmk.ln.app.aggregation.topic;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;
import tnmk.common.infrastructure.guardian.Guardian;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.ReflectionUtils;
import tnmk.ln.app.aggregation.topic.model.TopicComposite;
import tnmk.ln.app.aggregation.topic.model.TopicCompositeConverter;
import tnmk.ln.app.dictionary.ExpressionRepository;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.BaseExpression;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(TopicCompositeService.class);

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
            expressions.stream().forEach(expression -> prepareForSavingExpression(user, expression));
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

    private void prepareForSavingExpression(User user, Expression expression) {
        expression.setOwner(user);
        setRelatedExpressions(expression, "synonyms");
        setRelatedExpressions(expression, "antonyms");
        setRelatedExpressions(expression, "family");
    }

    private void setRelatedExpressions(Expression expression, String relationshipPropertyName) {
        List<BaseExpression> relatedExpressions = (List<BaseExpression>) ReflectionUtils.readProperty(expression, relationshipPropertyName);
        //Don't need because its saving will be override by later saving!
//        removeCascadeExpressionFromRelatedListOfItsRelations(expression, relatedExpressions, relationshipPropertyName);

        //Handling new related items
        if (CollectionUtils.isEmpty(relatedExpressions)) return;
        for (BaseExpression relatedExpression : relatedExpressions) {
            if (StringUtils.isEmpty(relatedExpression.getText()) || StringUtils.isNotBlank(relatedExpression.getId())) continue;
            //Update id of related expression into its properties
            Expression savedRelatedExpression = expressionRepository.findOneByLocaleAndText(expression.getLocale().getLanguage(), expression.getLocale().getCountry(), relatedExpression.getText());
            if (savedRelatedExpression != null) {
                relatedExpression.setId(savedRelatedExpression.getId());

                //Also update this expression into the list of relatedExpression.relatedItems
                //Don't need because its saving will be override by later saving!
//                addCascadeRelatedExpressionsIfNotExist(savedRelatedExpression, relationshipPropertyName, expression);
            }
        }
    }

    private void removeCascadeExpressionFromRelatedListOfItsRelations(BaseExpression expression, List<BaseExpression> relatedExpressions, String relationshipPropertyName) {
        //Handling removed related items
        if (StringUtils.isNotBlank(expression.getId())) {
            BaseExpression oldExpression = expressionRepository.findOne(expression.getId());
            Guardian.validateNotNull(oldExpression, "Not found the old expression with id " + expression.getId());

            List<BaseExpression> oldRelatedExpressions = (List<BaseExpression>) ReflectionUtils.readProperty(oldExpression, relationshipPropertyName);
            List<BaseExpression> removedItems = IterableUtil.findItemsNotInFirstListByField(relatedExpressions, oldRelatedExpressions, "text");
            for (BaseExpression removedItem : removedItems) {
                removeItemFromRelatedItems(removedItem.getId(), relationshipPropertyName, expression);
            }
        }
    }

    private void removeItemFromRelatedItems(String expressionId, String relationshipPropertyName, BaseExpression relatedExpression) {
        Expression expression = expressionRepository.findOne(expressionId);
        List<BaseExpression> relatedExpressions = (List<BaseExpression>) ReflectionUtils.readProperty(expression, relationshipPropertyName);
        if (!CollectionUtils.isEmpty(relatedExpressions)) {
            IterableUtil.removeItemsByFieldValue(relatedExpressions, relatedExpression, "text");
            ReflectionUtils.writeProperty(expression, relationshipPropertyName, relatedExpressions);
        }
        expressionRepository.save(expression);
    }

    private void addCascadeRelatedExpressionsIfNotExist(Expression expression, String relationshipPropertyName, BaseExpression relatedExpression) {
        List<BaseExpression> relatedExpressions = (List<BaseExpression>) ReflectionUtils.readProperty(expression, relationshipPropertyName);
        if (relatedExpressions == null) {
            relatedExpressions = new ArrayList<>();
        }
        //Compare by {@link Expression#text} field.
        BaseExpression itemHasSameFieldValue = IterableUtil.findItemHasSameFieldValue(relatedExpressions, relatedExpression, "text");
        BaseExpression addedExpression;
        if (itemHasSameFieldValue == null) {
            addedExpression = new Expression();
            relatedExpressions.add(addedExpression);
        } else {
            addedExpression = itemHasSameFieldValue;
        }
        addedExpression.setId(relatedExpression.getId());
        addedExpression.setText(relatedExpression.getText());
        ReflectionUtils.writeProperty(expression, relationshipPropertyName, relatedExpressions);
        expressionRepository.save(expression);
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
