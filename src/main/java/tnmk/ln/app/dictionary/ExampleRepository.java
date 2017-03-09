package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 3/9/17.
 */
@Component
public class ExampleRepository {
    @Autowired
    Neo4jRepository neo4jRepository;

    public Example findOneDetailById(long id) {
        return neo4jRepository.queryOneDetail(Example.class, id);
    }

    public int removeOneAndCompositions(long id) {
        String query = "MATCH (ex:Example) "
                + "where id(ex)={p0} "
                + "optional match "
                + "(ex)--(q:Question) detach delete ex,q";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }
}
