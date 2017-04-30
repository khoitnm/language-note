package tnmk.ln.app.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionPracticeResultQueryRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionPracticeResultQueryRepository.class);
    @Autowired
    private Neo4jRepository neo4jRepository;

    public QuestionPracticeResult findByOwnerIdAndQuestionId(Long userId, Long questionId) {
        String queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-question-practice-result-by-owner-and-expression.cql");
        return neo4jRepository.findOne(QuestionPracticeResult.class, queryString, userId, questionId);
    }

}