package tnmk.ln.app.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.ExpressionDetailRepository;
import tnmk.ln.app.practice.QuestionDetailRepository;

/**
 * @author khoi.tran on 3/31/17.
 *         This service is related to question also, so it cannot belong to ExpressionService
 */
@Service
public class ExpressionDeletionService {

    @Autowired
    private ExpressionDetailRepository expressionDetailRepository;

    @Autowired
    private QuestionDetailRepository questionDetailRepository;

    /**
     * Note: related entities include composited entities and other connected entities (question, answerResult...)
     *
     * @param expressionId
     */
    @Transactional
    public void deleteExpressionAndAllRelatedEntities(long expressionId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToExpression(expressionId);
        expressionDetailRepository.removeOneAndCompositions(expressionId);
    }
}
