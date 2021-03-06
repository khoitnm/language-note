package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tnmk.common.utils.datatype.NumberUtils;
import org.tnmk.ln.app.security.user.PossessionAuthorization;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 *         This service is related to question also, so it cannot belong to ExpressionService
 */
@Service
public class TopicDeletionService {

    @Autowired
    private ExpressionDeletionService expressionDeletionService;

    @Autowired
    private Neo4jRepository neo4jRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PossessionAuthorization possessionAuthorization;

    @Transactional
    public void detachExpressionFromTopic(User user, Long topicId, String expressionId) {
        Topic topic = neo4jRepository.validateExistOne(Topic.class, topicId);
        possessionAuthorization.validateCanRemovePossession(user, topic);
        neo4jRepository.removeElementInArraysOfNode(topicId, "expressionIds", expressionId);
    }

    @Transactional
    public void deleteTopicAndRelations(User user, String topicId, boolean removeExpression) {
        Topic topic;
        if (removeExpression) {
            topic = topicService.findDetailById(topicId);
            if (topic.getExpressionIds() != null) {
                for (String expressionId : topic.getExpressionIds()) {
//                    if (possessionAuthorization.canRemovePossession(user, expression)) {
                    expressionDeletionService.deleteExpressionAndRelations(expressionId);
//                    }
                }
            }
        } else {
            topic = topicService.findOneById(topicId);
        }
        possessionAuthorization.validateCanRemovePossession(user, topic);
        topicRepository.delete(NumberUtils.toLong(topicId));
    }
}
