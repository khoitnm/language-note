package tnmk.ln.app.aggregation.practice;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.common.util.ListUtil;
import tnmk.ln.app.aggregation.practice.model.QuestionComposite;
import tnmk.ln.app.aggregation.practice.model.QuestionConverter;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.ExpressionPracticeResultQueryRepository;
import tnmk.ln.app.practice.QuestionPartsRepository;
import tnmk.ln.app.practice.QuestionPracticeResultQueryRepository;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.question.QuestionParts;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import tnmk.ln.app.practice.model.QuestionWithPracticeResult;

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
        //TODO we have to use this sort because it looks like the CQL is not effective??
        ListUtil.sortByFields(result, "expressionPracticeResult.sumLatestAnswerPoint", "expressionPracticeResult.answers.size()", "expressionPracticeResult.sumTotalAnswerPoint");
//        log("After sort", result);
        return result;
    }

    private void log(String message, List<QuestionWithPracticeResult> result) {
        String questionAndPoints = result.stream()
                .map(question -> String.format("{question:%s,\t points:%s,\t answrtimes: %s,\t total points: %s}",
                        question.getQuestion().getId(),
                        question.getExpressionPracticeResult().getSumLatestAnswerPoint(),
                        question.getExpressionPracticeResult().getAnswers().size(),
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

        Expression expression = expressionService.findById(question.getFromExpressionId());
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
