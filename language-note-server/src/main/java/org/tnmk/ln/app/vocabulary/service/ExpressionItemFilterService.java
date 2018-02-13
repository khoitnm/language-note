package org.tnmk.ln.app.vocabulary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.vocabulary.entity.ExpressionItem;
import org.tnmk.ln.app.vocabulary.model.ExpressionFilter;
import org.tnmk.ln.app.vocabulary.repository.ExpressionItemFilterRepository;

import java.util.List;

/**
 * This class will filter suitable expressions for a user to test.
 * Suitable expressions usually have the least success correct answers or have not answered by the user yet.
 *
 * @author khoi.tran on 1/26/17.
 */
@Service
public class ExpressionItemFilterService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExpressionItemFilterService.class);
    public static final int MAX_EXPRESSION_ITEMS = 100;
    @Autowired
    private ExpressionItemFilterRepository expressionItemFilterRepository;

    /**
     * Filter order by count points.
     * Lesson by count points.
     *
     * @param userId
     * @param expressionFilter
     * @return
     */
    public List<ExpressionItem> filter(String userId, ExpressionFilter expressionFilter) {
//        Sort sort = new Sort(Arrays.asList(
//                new Sort.Order(Sort.Direction.DESC, "userPoints." + userId + ".favourite")
//                , new Sort.Order(Sort.Direction.ASC, "userPoints." + userId + ".latestAnswers.correctPercentage")
//                , new Sort.Order(Sort.Direction.ASC, "userPoints." + userId + ".answerDateTime")
//        ));
        Sort sort = new Sort(Sort.Direction.ASC
//                , "userPoints." + userId + ".favourite"
                , "userPoints." + userId + ".latestAnswers.correctPercentage"
                , "userPoints." + userId + ".answerDateTime"
        );
        int remainItems = MAX_EXPRESSION_ITEMS;
        List<ExpressionItem> result = expressionItemFilterRepository.filter(userId, expressionFilter, true, false, new PageRequest(0, remainItems, sort));
        remainItems = MAX_EXPRESSION_ITEMS - result.size();
        if (remainItems > 0) {
            result.addAll(expressionItemFilterRepository.filter(userId, expressionFilter, true, true, new PageRequest(0, remainItems, sort)));
        }
        remainItems = MAX_EXPRESSION_ITEMS - result.size();
        if (remainItems > 0) {
            result.addAll(expressionItemFilterRepository.filter(userId, expressionFilter, false, false, new PageRequest(0, remainItems, sort)));
        }
        remainItems = MAX_EXPRESSION_ITEMS - result.size();
        if (remainItems > 0) {
            result.addAll(expressionItemFilterRepository.filter(userId, expressionFilter, false, true, new PageRequest(0, remainItems, sort)));
        }

//        LOGGER.info("Answered: \n" + ObjectMapperUtil.toStringMultiLineForEachElement(result.stream().map(
//                item -> item.getUserPoints().getUserPoint(userId).getFavourite()
//                        + "\t" + item.getUserPoints().getUserPoint(userId).getLatestAnswers().getCorrectPercentage()
//                        + "\t" + item.getUserPoints().getUserPoint(userId).getAnswerDateTime()
//                        + "\t" + item.getExpression())
//                .collect(Collectors.toList())));
        return result;
    }
}
