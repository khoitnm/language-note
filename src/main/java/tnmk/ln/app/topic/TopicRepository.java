package tnmk.ln.app.topic;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.topic.entity.Topic;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
//@MongoRepoScanInclude
//public interface TopicRepository extends MongoRepository<Topic, String> {
@Neo4jRepoScanInclude
public interface TopicRepository extends Neo4jRepository<Topic, Long> {

}
