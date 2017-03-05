package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Topic;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface TopicRepository extends GraphRepository<Topic> {
    Topic findOneByText(String topicText);

    /**
     * Spring Data doesn't run with the @Query, it uses its own generated query
     *
     * @param text
     * @param userId
     * @return
     */
    @Deprecated
    @Query(value = "MATCH (n:`Topic`)<-[r:" + Topic.OWN_TOPIC + "]-(u:User) WHERE n.`text`={text} AND id(u)={userId}", count = true)
    Long countByTextAndOwnerId(String text, Long userId);
}
