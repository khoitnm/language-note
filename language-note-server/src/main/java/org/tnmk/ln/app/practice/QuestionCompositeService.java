package org.tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionCompositeService {
    @Autowired
    private QuestionDetailRepository questionDetailRepository;

    public void removeQuestionsAndCompositionsRelatedToExpression(String expressionId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToExpression(expressionId);
    }

}
