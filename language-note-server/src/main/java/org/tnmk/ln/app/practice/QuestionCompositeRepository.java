package org.tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.app.practice.entity.question.QuestionParts;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 4/25/17.
 */
@Component
public class QuestionCompositeRepository {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionPartsRepository questionPartsRepository;

    @Transactional
    public Question save(Question question) {
        if (question.getQuestionParts() != null) {
            QuestionParts questionParts = new QuestionParts();
            questionParts.setId(question.getQuestionPartsId());
            questionParts.setItems(question.getQuestionParts());
            questionParts = questionPartsRepository.save(questionParts);
            question.setQuestionPartsId(questionParts.getId());
        }
        Question result = questionRepository.save(question);
        return result;
    }

    public List<Question> save(List<Question> questions) {
        List<Question> result = questions.stream().map(iquestion -> save(iquestion)).collect(Collectors.toList());
        return result;
    }
}
