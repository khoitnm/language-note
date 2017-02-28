package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author khoi.tran on 2/26/17.
 */
@Service
public class QuestionGenerationService {
    @Autowired
    private QuestionRepository questionRepository;
//
//    @PostConstruct
//    public void test(){
//        Question question = new Question();
//        question.setQuestionType(QuestionType.EXPRESSION_RECALL);
//        questionRepository.save(question);
//
//    }
}
