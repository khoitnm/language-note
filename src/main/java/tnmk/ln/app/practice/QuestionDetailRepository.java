package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionDetailRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionDetailRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    public Question findOneDetailById(long id) {
        return neo4jRepository.queryOneDetail(Question.class, id);
    }

    public int removeOneAndCompositions(long id) {
        String query = "MATCH (q:Question) "
                + " WHERE id(q)={p0} "
                + " OPTIONAL MATCH (q)--(qp:QuestionPart) "
                + " OPTIONAL MATCH (q)--(pr:PracticeResult) "
                + " DETACH DELETE q, qp, pr";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }

    public int removeQuestionsAndCompositionsRelatedToExpression(long expressionId) {
        String query = "MATCH (q:Question)-[r:FROM_EXPRESSION]-(e:Expression) "
                + " WHERE id(e)={p0} "
                + " OPTIONAL MATCH (q)--(qp:QuestionPart) "
                + " OPTIONAL MATCH (q)--(pr:PracticeResult) "
                + " DETACH DELETE q, qp, pr";
        return neo4jRepository.execute(query, expressionId).getNodesDeleted();
    }

    public int removeQuestionsAndCompositionsRelatedToSenseGroup(long senseGroupId) {
        String query = "MATCH (q:Question)-[r:FROM_EXPRESSION]-(e:Expression)--(sg:SenseGroup) "
                + " WHERE id(sg)={p0} "
                + " OPTIONAL MATCH (q)--(qp:QuestionPart) "
                + " OPTIONAL MATCH (q)--(pr:PracticeResult) "
                + " DETACH DELETE q, qp, pr";
        return neo4jRepository.execute(query, senseGroupId).getNodesDeleted();

    }

    public int removeQuestionsAndCompositionsRelatedToSense(long senseId) {
        String query = "MATCH (q:Question)-[r:FROM_SENSE]-(s:Sense) "
                + " WHERE id(s)={p0} "
                + " OPTIONAL MATCH (q)--(qp:QuestionPart) "
                + " OPTIONAL MATCH (q)--(pr:PracticeResult) "
                + " DETACH DELETE q, qp, pr";
        return neo4jRepository.execute(query, senseId).getNodesDeleted();

    }

    public int removeQuestionsAndCompositionsRelatedToExample(long exampleId) {
        String query = "MATCH (q:Question)-[r:FROM_EXAMPLE]-(e:Example) "
                + " WHERE id(e)={p0} "
                + " OPTIONAL MATCH (q)--(qp:QuestionPart) "
                + " OPTIONAL MATCH (q)--(pr:PracticeResult) "
                + " DETACH DELETE q, qp, pr";
        return neo4jRepository.execute(query, exampleId).getNodesDeleted();

    }
}