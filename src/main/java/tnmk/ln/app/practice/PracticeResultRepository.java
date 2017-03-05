package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.PracticeResult;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import tnmk.ln.app.practice.entity.PracticeResult;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface PracticeResultRepository extends GraphRepository<PracticeResult> {
}
