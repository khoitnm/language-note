package tnmk.ln.app.dictionary;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import tnmk.ln.app.dictionary.entity.Expression;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface ExpressionRepository extends GraphRepository<Expression> {
}
