package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.common.util.ArrayUtil;
import tnmk.common.util.IterableUtil;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionRecommendationRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionRecommendationRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    /**
     * @param questionType
     * @param resultOwnerId this is the ownerId of result, not necessary to be the owner of questions, expressions, or topics.
     * @param topicIds
     * @return this request will get questions based on the aggregated result of expressions, not the result of senses or examples.
     */
    public List<Long> findQuestionIdsByRecommendedExpressions(long resultOwnerId, QuestionType questionType, Long... topicIds) {
        String queryString;
        if (ArrayUtil.isEmpty(topicIds)) {
            queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-question-ids-by-recommended-expressions.cql", questionType.getLogicName());
        } else {
            queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-question-ids-by-recommended-expressions-in-topics.cql", questionType.getLogicName());
        }
        return IterableUtil.toList(neo4jRepository.queryList(Long.class, queryString, resultOwnerId, topicIds));
    }

}