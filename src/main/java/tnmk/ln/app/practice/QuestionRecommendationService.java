package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionRecommendationRepository;
import tnmk.ln.app.practice.entity.QuestionType;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionRecommendationService {
    @Autowired
    private QuestionRecommendationRepository questionRecommendationRepository;

    public List<Question> loadQuestionsByNotes(long userId, QuestionType questionType, Long... noteIds) {
        return questionRecommendationRepository.loadQuestionsByRecommendedExpressions(userId, questionType, noteIds);
    }

}
