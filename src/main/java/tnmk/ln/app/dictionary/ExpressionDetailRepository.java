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

    public int removeOneAndCompositions(long id) {
        String query = "MATCH (e:Expression) "
                + "where id(e)={p0} "
                + "optional match "
                + "(e)--(sg:SenseGroup) "
                + "optional match "
                + "(sg)--(s:Sense) "
                + "optional match "
                + "(s)--(ex:Example) "
                + "optional match "
                + "(ex)--(q:Question) detach delete e,sg,s,ex,q";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }
}
