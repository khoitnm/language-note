package tnmk.ln.app.practice.entity;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

    /**
     * @param questionType
     * @param resultOwnerId this is the ownerId of result, not necessary to be the owner of questions, expressions, or notes.
     * @param noteIds
     * @return
     */
    //TODO it cannot load related objects
    public List<Question> loadQuestionsByLeastSuccessAnswer(long resultOwnerId, QuestionType questionType, Long... noteIds) {
        Guardian.assertArrayNotEmpty(noteIds, " param 'noteIds' must be not empty");
        String queryString = String.join("",
                "MATCH (q:", questionType.getLogicName(), " )-[:FROM_EXPRESSION]-(exp:Expression)-[:HAS_EXPRESSION]-(n:Note)"
                , " WHERE id(n) IN {p1} "
                , " OPTIONAL MATCH"
                , " (exp)-[:RESULT_OF_EXPRESSION]-(eprs:ExpressionPracticeResult)"
                , " OPTIONAL MATCH (eprs)--(o:User) WHERE id(o)={p0}"
                , " OPTIONAL MATCH (eprs)--(ap:AnswerPoint)"
                , " OPTIONAL MATCH (exp)--(sg:SenseGroup)"
                , " OPTIONAL MATCH (sg)--(s:Sense)"
                , " OPTIONAL MATCH (s)--(ex:Example)"
                , " WITH q, exp, sg, s, ex, eprs, ap, CASE eprs.latestAnswerPoints WHEN null THEN 0 END AS eprs_answer_points"
                , " ORDER BY eprs_answer_points ASC"
                , " RETURN q, exp,sg, s, ex, eprs, ap LIMIT 100"
        );
        return IterableUtil.toList(neo4jRepository.queryList(Question.class, queryString, resultOwnerId, noteIds));
    }

}