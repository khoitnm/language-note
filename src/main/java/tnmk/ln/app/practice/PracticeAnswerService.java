package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.infrastructure.guardian.Guardian;
import tnmk.common.util.ListUtil;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.result.AnswerResult;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/9/17.
 */
@Service
public class PracticeAnswerService {
    public static final int MAX_POINTS_STORING = 20;
    private static final int LATEST_POINTS = 2;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionPracticeResultRepository questionPracticeResultRepository;

    @Autowired
    private QuestionPracticeResultQueryRepository questionPracticeResultQueryRepository;

    @Autowired
    private ExpressionPracticeResultRepository expressionPracticeResultRepository;

    @Autowired
    private ExpressionPracticeResultQueryRepository expressionPracticeResultQueryRepository;

    @Transactional
    public AnswerResult answerResult(User user, long questionId, float answerPoint) {
        Question question = questionRepository.findOne(questionId, 3);
        Guardian.validateNotNull(question, "Question " + questionId + " doesn't exist. Cannot answer result for it");

        QuestionPracticeResult questionPracticeResult = saveQuestionAnswer(user, question, answerPoint);
        Expression expression = question.getFromExpression();
        Guardian.validateNotNull(expression, "Expression inside question must be not null.");
        ExpressionPracticeResult expressionPracticeResult = saveExpressionAnswer(user, expression, answerPoint);
        return new AnswerResult(expressionPracticeResult, questionPracticeResult);
    }

    private QuestionPracticeResult saveQuestionAnswer(User user, Question question, float answerPoint) {
        long questionId = question.getId();
        QuestionPracticeResult practiceResult = questionPracticeResultQueryRepository.findByOwnerIdAndQuestionId(user.getId(), questionId);
        if (practiceResult == null) {
            practiceResult = new QuestionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setQuestion(question);
        } else {
            ListUtil.addToListWithMaxSize(practiceResult.getAnswers(), answerPoint, MAX_POINTS_STORING);
        }
        practiceResult.setSumLatestAnswerPoint(calculateAnswerPoints(practiceResult.getAnswers(), LATEST_POINTS));
        return questionPracticeResultRepository.save(practiceResult);
    }

    private ExpressionPracticeResult saveExpressionAnswer(User user, Expression expression, float answerPoint) {
        long expressionId = expression.getId();
        ExpressionPracticeResult practiceResult = expressionPracticeResultQueryRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        if (practiceResult == null) {
            practiceResult = new ExpressionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setExpression(expression);
        } else {
            ListUtil.addToListWithMaxSize(practiceResult.getAnswers(), answerPoint, MAX_POINTS_STORING);
        }
        practiceResult.setSumLatestAnswerPoint(calculateAnswerPoints(practiceResult.getAnswers(), LATEST_POINTS));
        return expressionPracticeResultRepository.save(practiceResult);
    }

    private double calculateAnswerPoints(List<Float> answerPoints, int numPoints) {
        double result = 0;
        int startIndex = Math.max(0, answerPoints.size() - numPoints);
        for (int i = startIndex; i < answerPoints.size(); i++) {
            Float point = answerPoints.get(i);
            if (point != null) {
                result += point;
            }//else, consider point is 0
            i++;
        }
        return result;
    }
}
