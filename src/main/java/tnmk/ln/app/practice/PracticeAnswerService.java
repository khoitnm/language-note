package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.infrastructure.guardian.Guardian;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.query.AnswerPoint;
import tnmk.ln.app.practice.query.ExpressionPracticeResult;
import tnmk.ln.app.practice.query.QuestionPracticeResult;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/9/17.
 */
@Service
public class PracticeAnswerService {
    @Autowired
    private QuestionPracticeResultRepository questionPracticeResultRepository;

    @Autowired
    private ExpressionPracticeResultRepository expressionPracticeResultRepository;

    @Transactional
    public ExpressionPracticeResult answerResult(User user, Question question, AnswerPoint answerPoint) {
        QuestionPracticeResult questionPracticeResult = saveQuestionAnswer(user, question, answerPoint);
        Expression expression = question.getFromExpression();
        Guardian.assertNotNull(expression, "Expression inside question must be not null.");
        ExpressionPracticeResult expressionPracticeResult = saveExpressionAnswer(user, expression, answerPoint);
        return expressionPracticeResult;
    }

    private QuestionPracticeResult saveQuestionAnswer(User user, Question question, AnswerPoint answerPoint) {
        long questionId = question.getId();
        QuestionPracticeResult practiceResult = questionPracticeResultRepository.findByOwnerIdAndQuestionId(user.getId(), questionId);
        if (practiceResult == null) {
            practiceResult = new QuestionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setQuestion(question);
        } else {
            practiceResult.getAnswers().add(answerPoint);
        }
        practiceResult.setLatestAnswerPoints(calculateAnswerPoints(practiceResult.getAnswers()));
        return questionPracticeResultRepository.save(practiceResult);
    }

    private ExpressionPracticeResult saveExpressionAnswer(User user, Expression expression, AnswerPoint answerPoint) {
        long expressionId = expression.getId();
        ExpressionPracticeResult practiceResult = expressionPracticeResultRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        if (practiceResult == null) {
            practiceResult = new ExpressionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setExpression(expression);
        } else {
            practiceResult.getAnswers().add(answerPoint);
        }
        practiceResult.setLatestAnswerPoints(calculateAnswerPoints(practiceResult.getAnswers()));
        return expressionPracticeResultRepository.save(practiceResult);
    }

    private double calculateAnswerPoints(List<AnswerPoint> answerPointList) {
        int totalMaxPoints = 0;
        int totalCorrectPoints = 0;
        for (AnswerPoint answerPoint : answerPointList) {
            totalMaxPoints += answerPoint.getMaxPoints();
            totalCorrectPoints += answerPoint.getCorrectPoints();
        }
        return (double) totalCorrectPoints / totalMaxPoints;
    }
}
