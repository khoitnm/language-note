package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 3/9/17.
 */
@Component
public class ExpressionDetailRepository {
    @Autowired
    Neo4jRepository neo4jRepository;

    public Expression findOneDetailById(long id) {
        return neo4jRepository.queryOneDetail(Expression.class, id);
    }

    public int removeExpressionAndCompositions(long expressionId) {
        String query = "MATCH (e:Expression) "
                + " where id(e)={p0} "
                + " optional match "
                + " (e)--(sg:SenseGroup) "
                + " optional match "
                + " (sg)--(s:Sense) "
                + " optional match "
                + " (s)--(ex:Example) "
                + " detach delete e,sg,s,ex";
        return neo4jRepository.execute(query, expressionId).getNodesDeleted();
    }

    public int removeSenseGroupAndCompositions(long senseGroupId) {
        String query = " MATCH (sg:SenseGroup) "
                + " WHERE id(sg) = {p0}"
                + " optional match "
                + " (sg)--(s:Sense) "
                + " optional match "
                + " (s)--(ex:Example) "
                + " detach delete sg,s,ex";
        return neo4jRepository.execute(query, senseGroupId).getNodesDeleted();
    }

    public int removeSenseAndCompositions(long senseId) {
        String query = " MATCH (s:Sense) "
                + " WHERE id(s) = {p0}"
                + " optional match "
                + " (s)--(ex:Example) "
                + " detach delete s,ex";
        return neo4jRepository.execute(query, senseId).getNodesDeleted();
    }

    public int removeExampleAndCompositions(long exampleId) {
        String query = " MATCH (ex:Example) "
                + " WHERE id(ex) = {p0}"
                + " detach delete ex";
        return neo4jRepository.execute(query, exampleId).getNodesDeleted();
    }
}
