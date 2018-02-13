package org.tnmk.ln.app.aggregation.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.ListUtils;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.app.practice.entity.question.QuestionType;
import org.tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import org.tnmk.common.util.IterableUtil;
import org.tnmk.ln.app.topic.TopicDetailRepository;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionRecommendationRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionRecommendationRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    @Autowired
    private TopicDetailRepository topicDetailRepository;

    /**
     * @param questionType
     * @param resultOwnerId this is the ownerId of result, not necessary to be the owner of questions, expressions, or topics.
     * @param topicIds
     * @return this request will get questions based on the aggregated result of expressions, not the result of senses or examples.
     */
    public List<Question> findQuestionIdsByRecommendedExpressions(long resultOwnerId, QuestionType questionType, List<Long> topicIds) {
        String queryString;
        if (ListUtils.isEmpty(topicIds)) {
            queryString = ClassPathQueryLoader.loadQuery("/org/tnmk/ln/app/practice/query/load-question-ids-by-recommended-expressions.cql", questionType.getLogicName());
            return IterableUtil.toList(neo4jRepository.findList(Question.class, queryString, resultOwnerId));
        } else {
            List<Topic> topics = topicDetailRepository.findByIdIn(topicIds);
            List<String> expressionIdsInTopics = topics.stream().flatMap(itopic -> itopic.getExpressionIds().stream()).collect(Collectors.toList());
            queryString = ClassPathQueryLoader.loadQuery("/org/tnmk/ln/app/practice/query/load-question-ids-by-recommended-expressions-in-list.cql", questionType.getLogicName());
            return IterableUtil.toList(neo4jRepository.findList(Question.class, queryString, resultOwnerId, expressionIdsInTopics));
        }
    }

}