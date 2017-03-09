package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.dictionary.entity.SenseGroup;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 3/9/17.
 */
@Component
public class SenseGroupRepository {
    @Autowired
    Neo4jRepository neo4jRepository;

    public SenseGroup findOneDetailById(long id) {
        return neo4jRepository.queryOneDetail(SenseGroup.class, id);
    }

    public int removeOneAndCompositions(long id) {
        String query = "MATCH (sg:SenseGroup) "
                + "where id(sg)={p0} "
                + "optional match "
                + "(sg)--(s:Sense) "
                + "optional match "
                + "(s)--(ex:Example) "
                + "optional match "
                + "(ex)--(q:Question) detach delete sg,s,ex,q";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }
}
