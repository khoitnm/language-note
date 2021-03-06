package org.tnmk.ln.infrastructure.security.usersmanagement.neo4j;



import org.springframework.data.neo4j.repository.GraphRepository;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface UserRepository extends GraphRepository<User> {
    User findOneByUsername(String username);
}
