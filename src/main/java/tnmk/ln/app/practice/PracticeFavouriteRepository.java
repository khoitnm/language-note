package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.PracticeResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.util.IterableUtil;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface PracticeFavouriteRepository extends GraphRepository<PracticeFavourite> {
}
