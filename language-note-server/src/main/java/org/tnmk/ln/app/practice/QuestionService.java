package org.tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionLoadingRepository questionLoadingRepository;

    public List<Question> loadQuestionsByTopics(User user, Long... topicIds) {
        return questionLoadingRepository.loadQuestionsByTopics(user, topicIds);
    }

}
