package org.tnmk.ln.app.aggregation.practice;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.utils.collections.ListUtils;
import org.tnmk.ln.app.aggregation.practice.model.QuestionComposite;
import org.tnmk.ln.app.aggregation.topic.ExpressionCompositeService;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.practice.ExpressionPracticeResultQueryRepository;
import org.tnmk.ln.app.practice.QuestionPartsRepository;
import org.tnmk.ln.app.practice.QuestionPracticeResultQueryRepository;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.app.practice.entity.question.QuestionParts;
import org.tnmk.ln.app.practice.entity.question.QuestionType;
import org.tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import org.tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import org.tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.aggregation.practice.model.QuestionConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionRecommendationService {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionRecommendationService.class);

    @Autowired
    private QuestionConverter questionConverter;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private ExpressionCompositeService expressionCompositeService;

    @Autowired
    private QuestionPartsRepository questionPartsRepository;

    @Autowired
    private QuestionRecommendationRepository questionRecommendationRepository;

    @Autowired
    private ExpressionPracticeResultQueryRepository expressionPracticeResultRepository;

    @Autowired
    private QuestionPracticeResultQueryRepository questionPracticeResultRepository;

    public List<QuestionWithPracticeResult> loadQuestionsByTopics(long userId, QuestionType questionType, List<Long> topicIds) {
        List<Question> questions = questionRecommendationRepository.findQuestionIdsByRecommendedExpressions(userId, questionType, topicIds);
//        List<String> questionIds = questions.stream().map(question -> String.format("{q:%s, e:%s}", question.getId(), question.getFromExpressionId())).collect(Collectors.toList());
//        LOGGER.debug("Question Ids:" + questionIds.toString());

        List<Question> distinctQuestions = filterQuestionsDistintExpression(questions);
        List<QuestionWithPracticeResult> result = distinctQuestions.stream().map(question -> mapToQuestionWithPracticeResult(userId, question)).filter(questionWithPracticeResult -> questionWithPracticeResult != null).collect(Collectors.toList());
        //TODO We have to use this sort because it looks like the CQL is not effective!? Is there any other way which don't need to resort again like this?
        ListUtils.sortByFields(result, "expressionPracticeResult.sumLatestAnswerPoint", "#expressionPracticeResult != null ? (expressionPracticeResult.answers != null ? expressionPracticeResult.answers.size() : 0) : 0", "expressionPracticeResult.sumTotalAnswerPoint");
        log("After sort", result);
        return result;
    }

    private void log(String message, List<QuestionWithPracticeResult> result) {
        String questionAndPoints = result.stream()
                .map(question -> String.format("{question:%s,\t points:%s,\t favourite: %s,\t answrtimes: %s,\t total points: %s}",
                        question.getQuestion().getId(),
                        question.getExpressionPracticeResult().getSumLatestAnswerPoint(),
                        question.getExpressionPracticeResult().getAdditionalPoints(),
                        question.getExpressionPracticeResult().getAnswers() != null ? question.getExpressionPracticeResult().getAnswers().size() : 0,
                        question.getExpressionPracticeResult().getSumTotalAnswerPoint()))
                .collect(Collectors.joining("\n\t"));
        LOGGER.debug("Questions And Points: {} \n\t {}", message, questionAndPoints);
    }

    private List<Question> filterQuestionsDistintExpression(List<Question> questions) {
        List<Question> result = new ArrayList<>();
        //Initiate the grouping map and put value into groups
        //We need to keep the inserting order, that's why LinkedHashMap is the chosen data structure.
        Map<String, List<Question>> questionsGroupByExpression = new LinkedHashMap<>();
        for (Question question : questions) {
            List<Question> group = questionsGroupByExpression.get(question.getFromExpressionId());
            if (group == null) {
                group = new ArrayList<>();
                questionsGroupByExpression.put(question.getFromExpressionId(), group);
            }
            group.add(question);
        }

        //Select a random item inside each group.
        for (List<Question> questionsGroup : questionsGroupByExpression.values()) {
            int index = RandomUtils.nextInt(0, questionsGroup.size());
            result.add(questionsGroup.get(index));
        }
        for (Question question : result) {
            if (StringUtils.isNotBlank(question.getQuestionPartsId())) {
                QuestionParts questionParts = questionPartsRepository.findOne(question.getQuestionPartsId());
                question.setQuestionParts(questionParts.getItems());
            }
        }
        return result;
    }

    private QuestionWithPracticeResult mapToQuestionWithPracticeResult(long userId, Question question) {
        QuestionWithPracticeResult questionWithPracticeResult = new QuestionWithPracticeResult();

        ExpressionComposite expression = expressionCompositeService.findById(userId, question.getFromExpressionId());
        if (expression == null) return null;

        QuestionComposite questionComposite = questionConverter.toModel(question);
        questionComposite.setFromExpression(expression);
        questionWithPracticeResult.setQuestion(questionComposite);

        ExpressionPracticeResult expressionPracticeResult = expressionPracticeResultRepository.findByOwnerIdAndExpressionId(userId, question.getFromExpressionId());
        questionWithPracticeResult.setExpressionPracticeResult(expressionPracticeResult);

        QuestionPracticeResult questionPracticeResult = questionPracticeResultRepository.findByOwnerIdAndQuestionId(userId, question.getId());
        questionWithPracticeResult.setQuestionPracticeResult(questionPracticeResult);

        return questionWithPracticeResult;
    }

}
