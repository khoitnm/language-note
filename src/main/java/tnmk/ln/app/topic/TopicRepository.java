package tnmk.ln.app.topic;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.topic.entity.Topic;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface TopicRepository extends GraphRepository<Topic> {
    List<Topic> findByOwnerId(Long ownerId);
}
