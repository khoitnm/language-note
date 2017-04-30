package tnmk.ln.app.practice;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface PracticeFavouriteRepository extends GraphRepository<PracticeFavourite> {
}
