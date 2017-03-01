package tnmk.ln.app.user.entity;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface AccountRepository extends GraphRepository<Account> {
}
