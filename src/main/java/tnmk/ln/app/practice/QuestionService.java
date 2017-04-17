package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionLoadingRepository questionLoadingRepository;

    public void removeQuestionsAndCompositionsRelatedToExpression(String expressionId){

    }


    public List<Question> loadQuestionsByTopics(User user, Long... topicIds) {
        return questionLoadingRepository.loadQuestionsByTopics(user, topicIds);
    }

}
