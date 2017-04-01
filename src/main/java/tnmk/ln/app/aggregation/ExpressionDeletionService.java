package tnmk.ln.app.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.ExpressionDetailRepository;
import tnmk.ln.app.practice.QuestionDetailRepository;
import tnmk.ln.app.security.user.PossessionAuthorization;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.TopicService;

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

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PossessionAuthorization possessionAuthorization;

    /**
     * This method don't check permission. So call it only after checking permission
     * Note: related entities include composited entities and other connected entities (question, answerResult...)
     *
     * @param expressionId
     */
    @Transactional
    void deleteExpressionAndRelations(long expressionId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToExpression(expressionId);
        expressionDetailRepository.removeExpressionAndCompositions(expressionId);
    }

    @Transactional
    void deleteSenseGroupAndRelations(long senseGroupId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToSenseGroup(senseGroupId);
        expressionDetailRepository.removeExpressionAndCompositions(senseGroupId);
    }

    @Transactional
    void deleteSenseAndRelations(long senseId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToSense(senseId);
        expressionDetailRepository.removeSenseAndCompositions(senseId);
    }
    @Transactional
    void deleteExampleAndRelations(long exampleId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToExample(exampleId);
        expressionDetailRepository.removeExampleAndCompositions(exampleId);
    }
}
