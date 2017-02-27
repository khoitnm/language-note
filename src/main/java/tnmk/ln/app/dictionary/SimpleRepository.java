package tnmk.ln.app.dictionary;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import tnmk.ln.app.dictionary.entity.Simple;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public interface SimpleRepository extends GraphRepository<Simple> {
}
