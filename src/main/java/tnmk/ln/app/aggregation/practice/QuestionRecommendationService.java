package tnmk.ln.app.aggregation.practice;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.aggregation.practice.model.QuestionComposite;
import tnmk.ln.app.aggregation.practice.model.QuestionConverter;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.ExpressionPracticeResultQueryRepository;
import tnmk.ln.app.practice.QuestionPracticeResultQueryRepository;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionRecommendationService {
    @Autowired
    private QuestionConverter questionConverter;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private QuestionRecommendationRepository questionRecommendationRepository;

    @Autowired
    private ExpressionPracticeResultQueryRepository expressionPracticeResultRepository;

    @Autowired
    private QuestionPracticeResultQueryRepository questionPracticeResultRepository;

    @Autowired
    private Neo4jRepository neo4jRepository;

    public List<QuestionWithPracticeResult> loadQuestionsByTopics(long userId, QuestionType questionType, List<Long> topicIds) {
        List<Long> questionIds = questionRecommendationRepository.findQuestionIdsByRecommendedExpressions(userId, questionType, topicIds);
        List<Question> questions = neo4jRepository.findDetails(questionType.getQuestionClass(), questionIds);
        questions = filterQuestionsWithFromSameExpression(questions);
        List<QuestionWithPracticeResult> result = questions.stream().map(question -> mapToQuestionWithPracticeResult(userId, question)).filter(questionWithPracticeResult -> questionWithPracticeResult != null).collect(Collectors.toList());
        return result;
    }

    private List<Question> filterQuestionsWithFromSameExpression(List<Question> questions) {
        List<Question> result = new ArrayList<>();
        Map<String, List<Question>> questionsGroupByExpression = new HashMap<>();
        for (Question question : questions) {
            List<Question> group = questionsGroupByExpression.get(question.getFromExpressionId());
            if (group == null) {
                group = new ArrayList<>();
                questionsGroupByExpression.put(question.getFromExpressionId(), group);
            }
            group.add(question);
        }

        for (List<Question> questionsGroup : questionsGroupByExpression.values()) {
            int index = RandomUtils.nextInt(0, questionsGroup.size());
            result.add(questionsGroup.get(index));
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
//        ExpressionPracticeResultComposite expressionPracticeResultComposite = expressionPracticeResultConverter.toModel(expressionPracticeResult);
//        if (expressionPracticeResultComposite != null) {
//            expressionPracticeResultComposite.setExpression(expression);
//        }
        questionWithPracticeResult.setExpressionPracticeResult(expressionPracticeResult);

        QuestionPracticeResult questionPracticeResult = questionPracticeResultRepository.findByOwnerIdAndQuestionId(userId, question.getId());
        questionWithPracticeResult.setQuestionPracticeResult(questionPracticeResult);

        return questionWithPracticeResult;
    }

}
