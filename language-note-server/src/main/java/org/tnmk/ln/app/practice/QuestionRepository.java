package org.tnmk.ln.app.practice;



import org.springframework.data.neo4j.repository.GraphRepository;
import org.tnmk.ln.app.practice.entity.question.Question;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface QuestionRepository extends GraphRepository<Question> {
}
