package tnmk.ln.app.datastructure;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 3/30/17.
 */
@Neo4jRepoScanInclude
public interface NodeWithPropsRepositories extends Neo4jRepository<NodeWithProps, Long> {
}
