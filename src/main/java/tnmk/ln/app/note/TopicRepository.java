package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Topic;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.infrastructure.repositoriesfilter.Neo4jRepoScanInclude;
import tnmk.ln.app.note.entity.Topic;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface TopicRepository extends GraphRepository<Topic> {
}