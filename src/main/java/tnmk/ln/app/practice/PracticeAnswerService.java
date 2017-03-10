package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.practice.entity.AnswerPoint;
import tnmk.ln.app.practice.entity.PracticeResult;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/9/17.
 */
@Service
public class PracticeAnswerService {
    @Autowired
    private PracticeResultRepository practiceResultRepository;

    @Transactional
    public PracticeResult answerResult(User user, Question question, AnswerPoint answerPoint) {
        PracticeResult practiceResult = practiceResultRepository.findByOwnerIdAndQuestionId(user.getId(), question.getId());
        if (practiceResult == null) {
            practiceResult = new PracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
        } else {
            practiceResult.getAnswers().add(answerPoint);
        }
        practiceResult.setLatestAnswerPoints(calculateAnswerPoints(practiceResult.getAnswers()));
        return practiceResultRepository.save(practiceResult);
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
