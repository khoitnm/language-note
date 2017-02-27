package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.infrastructure.repositoriesfilter.Neo4jRepoScanInclude;
import tnmk.ln.app.practice.entity.Question;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface QuestionRepository extends GraphRepository<Question> {
}