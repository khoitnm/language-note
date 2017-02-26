package tnmk.ln.app.dictionary;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.dictionary.entity.Expression;

/**
 * @author khoi.tran on 2/26/17.
 */
public interface ExpressionRepository extends GraphRepository<Expression> {
}
