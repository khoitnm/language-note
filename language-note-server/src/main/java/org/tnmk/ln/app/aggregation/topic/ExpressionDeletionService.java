package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.practice.QuestionCompositeService;
import org.tnmk.ln.app.security.user.PossessionAuthorization;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.app.topic.TopicService;

/**
 * @author khoi.tran on 3/31/17.
 *         This service is related to question also, so it cannot belong to ExpressionService
 */
@Service
public class ExpressionDeletionService {

//    @Autowired
//    private ExpressionDetailNeo4jRepository expressionDetailNeo4jRepository;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private QuestionCompositeService questionCompositeService;

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
    void deleteExpressionAndRelations(String expressionId) {
        questionCompositeService.removeQuestionsAndCompositionsRelatedToExpression(expressionId);
        expressionService.deleteCompositeById(expressionId);
    }
//
//    @Transactional
//    public void deletePhotoAndRelations(Long photoId) {
//        expressionDetailNeo4jRepository.detachPhotoFromSense(photoId);
//    }
}
