package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class ExpressionPracticeResultQueryRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExpressionPracticeResultQueryRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    public ExpressionPracticeResult findByOwnerIdAndExpressionId(Long userId, Long expressionId) {
        String queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-expression-practice-result-by-owner-and-expression.cql");
        return neo4jRepository.findOne(ExpressionPracticeResult.class, queryString, userId, expressionId);
    }

}