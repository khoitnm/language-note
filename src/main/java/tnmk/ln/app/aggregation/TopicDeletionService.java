package tnmk.ln.app.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.ExpressionDetailRepository;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.practice.QuestionDetailRepository;
import tnmk.ln.app.security.user.PossessionAuthorization;
import tnmk.ln.app.topic.TopicRepository;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 *         This service is related to question also, so it cannot belong to ExpressionService
 */
@Service
public class TopicDeletionService {

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
     * Note: related entities include composited entities and other connected entities (question, answerResult...)
     *
     * @param expressionId
     */
    @Transactional
    public void deleteExpressionAndAllRelatedEntities(long expressionId) {
        questionDetailRepository.removeQuestionsAndCompositionsRelatedToExpression(expressionId);
        expressionDetailRepository.removeOneAndCompositions(expressionId);
    }

    @Transactional
    public void deleteTopicAndRelations(User user, Long topicId, boolean removeExpression) {
        Topic topic;
        if (removeExpression) {
            topic = topicService.findDetailById(topicId);
            if (topic.getExpressions() != null) {
                for (Expression expression : topic.getExpressions()) {
                    if (possessionAuthorization.canRemovePossession(user, expression)) {
                        deleteExpressionAndAllRelatedEntities(expression.getId());
                    }
                }
            }
        } else {
            topic = topicService.findOneById(topicId);
        }
        possessionAuthorization.validateCanRemovePossession(user, topic);
        topicRepository.delete(topicId);
    }
}
