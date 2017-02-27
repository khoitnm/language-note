package tnmk.ln.app.example;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.infrastructure.repositoriesfilter.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/27/17.
 */
@Neo4jRepoScanInclude
public interface ExampleRepository extends GraphRepository<Example> {
}
