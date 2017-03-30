package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.PracticeResult;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import tnmk.ln.app.practice.entity.answer.QuestionPracticeResult;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface QuestionPracticeResultRepository extends GraphRepository<QuestionPracticeResult> {
    QuestionPracticeResult findByOwnerIdAndQuestionId(Long userId, Long questionId);
}
