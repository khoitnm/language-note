package tnmk.ln.app.dictionary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import tnmk.ln.app.dictionary.entity.Expression;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
//@Neo4jRepoScanInclude
@MongoRepoScanInclude
public interface ExpressionRepository extends MongoRepository<Expression, String> {
    @Query(value = "{'_id': {$in: [?0]}}")
    List<Expression> findByIdIn(List<String> expressionIds);

    Expression findOneByText(String text);

    @Query(value = "{'text': ?0}", fields = "{'_id':1, 'text':1}")
    Expression findOneBriefByText(String trimmedText);
}
