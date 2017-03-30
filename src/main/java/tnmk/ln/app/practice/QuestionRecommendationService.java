package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.practice.entity.answer.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.answer.QuestionPracticeResult;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionRecommendationService {
    @Autowired
    private QuestionRecommendationRepository questionRecommendationRepository;

    @Autowired
    private ExpressionPracticeResultRepository expressionPracticeResultRepository;

    @Autowired
    private QuestionPracticeResultRepository questionPracticeResultRepository;

    @Autowired
    private Neo4jRepository neo4jRepository;

    public List<QuestionWithPracticeResult> loadQuestionsByTopics(long userId, QuestionType questionType, Long... topicIds) {
        List<Long> questionIds = questionRecommendationRepository.findQuestionIdsByRecommendedExpressions(userId, questionType, topicIds);
        List<Question> questions = neo4jRepository.queryDetails(questionType.getQuestionClass(), questionIds);
        List<QuestionWithPracticeResult> result = questions.stream().map(question -> mapToQuestionWithPracticeResult(userId, question)).collect(Collectors.toList());
        return result;
    }

    private QuestionWithPracticeResult mapToQuestionWithPracticeResult(long userId, Question question) {
        QuestionWithPracticeResult questionWithPracticeResult = new QuestionWithPracticeResult();
        questionWithPracticeResult.setQuestion(question);

        ExpressionPracticeResult expressionPracticeResult = expressionPracticeResultRepository.findByOwnerIdAndExpressionId(userId, question.getFromExpression().getId());
        questionWithPracticeResult.setExpressionPracticeResult(expressionPracticeResult);

        QuestionPracticeResult questionPracticeResult = questionPracticeResultRepository.findByOwnerIdAndQuestionId(userId, question.getId());
        questionWithPracticeResult.setQuestionPracticeResult(questionPracticeResult);

        return questionWithPracticeResult;
    }

}
