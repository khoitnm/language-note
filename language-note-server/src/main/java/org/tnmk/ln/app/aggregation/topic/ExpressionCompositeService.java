package org.tnmk.ln.app.aggregation.topic;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.practice.QuestionGenerationService;
import org.tnmk.common.infrastructure.guardian.Guardian;
import org.tnmk.common.util.IterableUtil;
import org.tnmk.common.util.ReflectionUtils;
import org.tnmk.ln.app.aggregation.practice.ExpressionCompositeConverter;
import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.dictionary.ExpressionRepository;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.dictionary.entity.BaseExpression;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.app.practice.PracticeFavouriteService;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 4/30/17.
 */
@Service
public class ExpressionCompositeService {
    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionCompositeConverter expressionCompositeConverter;

    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    @Autowired
    private QuestionGenerationService questionService;

    public ExpressionComposite findById(Long userId, String expressionId) {
        Expression expression = expressionService.findById(expressionId);
        return expressionCompositeConverter.toExpressionComposite(userId, expression);
    }

    /**
     * The logic is similar to {@link TopicCompositeService#saveTopicAndRelations(User, TopicComposite)}. The only difference is that method optimized for saving many expressions at the same time.
     *
     * @param user
     * @param expression
     * @return
     */
    public ExpressionComposite saveExpression(User user, ExpressionComposite expression) {
        prepareForSavingExpression(user, expression);

        expression = expressionRepository.save(expression);
        practiceFavouriteService.saveExpressionFavourite(user, expression.getId(), expression.getFavourite());
//        expression = expressionCompositeConverter.toExpressionComposite(user.getId(), expression);
        questionService.createQuestionsIfNotExist(expression);
        return expression;
    }

    public void prepareForSavingExpression(User user, ExpressionComposite expression) {
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

    public List<ExpressionComposite> findByKeyword(User user, String localeString, String keyword) {
        Locale locale = Locale.fromString(localeString);
        String normKeyword = StringUtils.normalizeSpace(keyword.toLowerCase());
        List<Expression> expressions = expressionRepository.findByLocaleAndContainText(locale.getLanguage(), locale.getCountry(), normKeyword);
        return expressionCompositeConverter.toExpressionComposites(user.getId(), expressions);
    }
}
