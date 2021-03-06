package org.tnmk.ln.app.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tnmk.ln.app.practice.entity.question.QuestionType;
import org.tnmk.common.utils.collections.IterableUtils;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.Iterator;
import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionLoadingRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionLoadingRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    public List<Question> loadQuestionsByTopics(User user, Long... topicIds) {
        String queryString = String.join("",
                "MATCH", " (q:Question)-[:FROM_EXPRESSION]-(e:Expression)<-[:HAS_EXPRESSION]-(n:Topic) "
                , " WHERE id(n) IN {p1}"
                , " MATCH (e)-[:OWN_EXPRESSION]-(u:User)"
                , " WHERE id(u)={p0}"
                , " OPTIONAL MATCH (e)--(sg:SenseGroup)--(s:Sense)--(ex:Example)"
                , " RETURN q,e,sg,s,ex"
        );
        return IterableUtils.toList(neo4jRepository.findList(Question.class, queryString, user.getId(), topicIds));
    }

    public Question findOneByQuestionTypeAndFromExpressionIdAndFromSenseId(QuestionType questionType, String expressionId, String senseId) {
        String queryString = "MATCH (n:`" + questionType.getLogicName() + "`)"
                + " WHERE n.fromExpressionId = {p0} AND n.fromSenseId = {p1} "
                + " WITH n "
                + " MATCH p=(n)-[*0..1]-(m) "
                + " RETURN p, ID(n)";
        return findOne(Question.class, queryString, expressionId, senseId);
    }

    public Question findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType questionType, String expressionId, String senseId, String exampleId) {
//        String queryString = "MATCH (n:`" + questionType.getLogicName() + "`)"
//                + "MATCH (m0:`Expression`) WHERE id(m0) = {p0} "
//                + "MATCH (m1:`Sense`) WHERE id(m1) = {p1} "
//                + "MATCH (m2:`Example`) WHERE id(m2) = {p2} "
//                + "MATCH (n)-[:`FROM_EXPRESSION`]->(m0) MATCH (n)-[:`FROM_SENSE`]->(m1) "
//                + "MATCH (n)-[:`FROM_EXAMPLE`]->(m2) WITH n "
//                + "MATCH p=(n)-[*0..1]-(m) RETURN p, ID(n)";
        String queryString = "MATCH (n:`" + questionType.getLogicName() + "`)"
                + " WHERE n.fromExpressionId = {p0} "
                + " AND n.fromSenseId = {p1} "
                + " AND n.fromExampleId = {p2} "
//                + "MATCH (n)-[:`FROM_EXPRESSION`]->(m0) MATCH (n)-[:`FROM_SENSE`]->(m1) "
//                + "MATCH (n)-[:`FROM_EXAMPLE`]->(m2)
                + " WITH n "
                + " MATCH p=(n)-[*0..1]-(m) RETURN p, ID(n)";
        return findOne(Question.class, queryString, expressionId, senseId, exampleId);
    }

    private <T> T findOne(Class<T> entityClass, String queryString, Object... params) {
        T result;
        Iterable<T> questions = neo4jRepository.findList(entityClass, queryString, params);
        Iterator<T> iterator = questions.iterator();
        if (iterator.hasNext()) {
            result = iterator.next();
            if (iterator.hasNext()) {
                LOGGER.warn("The query should has only one result. But now we find it more than one");
            }
        } else {
            result = null;
        }
        return result;
    }

}