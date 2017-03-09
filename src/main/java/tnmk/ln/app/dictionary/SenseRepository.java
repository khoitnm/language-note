package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 3/9/17.
 */
@Component
public class SenseRepository {
    @Autowired
    Neo4jRepository neo4jRepository;

    public Sense findOneDetailById(long id) {
        return neo4jRepository.queryOneDetail(Sense.class, id);
    }

    public int removeOneAndCompositions(long id) {
        String query = "MATCH (s:Sense) "
                + "where id(s)={p0} "
                + "optional match "
                + "(s)--(ex:Example) "
                + "optional match "
                + "(ex)--(q:Question) detach delete s,ex,q";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }
}
