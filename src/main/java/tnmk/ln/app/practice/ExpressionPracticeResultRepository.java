package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.PracticeResult;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.practice.entity.answer.ExpressionPracticeResult;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface ExpressionPracticeResultRepository extends GraphRepository<ExpressionPracticeResult> {
    ExpressionPracticeResult findByOwnerIdAndExpressionId(Long userId, Long expressionId);

    List<ExpressionPracticeResult> findByOwnerIdAndExpressionIds(Long userId, List<Long> expressionId);
}
