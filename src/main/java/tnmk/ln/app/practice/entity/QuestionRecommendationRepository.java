package tnmk.ln.app.practice.entity;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.common.infrastructure.guardian.Guardian;
import tnmk.common.util.IterableUtil;
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
//    @Autowired
//    private ClassPathQueryLoader queryLoader;

    /**
     * @param questionType
     * @param resultOwnerId this is the ownerId of result, not necessary to be the owner of questions, expressions, or notes.
     * @param noteIds
     * @return this request will get questions based on the aggregated result of expressions, not the result of senses or examples.
     */
    //TODO it cannot load related objects
    public List<Question> loadQuestionsByRecommendedExpressions(long resultOwnerId, QuestionType questionType, Long... noteIds) {
        Guardian.assertArrayNotEmpty(noteIds, " param 'noteIds' must be not empty");
        String queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-questions-by-recommended-expressions.cql", questionType.getLogicName(), questionType.getLogicName());
        return IterableUtil.toList(neo4jRepository.queryList(Question.class, queryString, resultOwnerId, noteIds));
    }

}