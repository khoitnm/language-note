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
    @Query(value = "{'_id': {$in: ?0}}")
    List<Expression> findByIdIn(List<String> expressionIds);

    Expression findOneByText(String text);

    @Query(value = "{'text': ?2, 'locale.language': ?0, 'locale.country': ?1}")
    Expression findOneByLocaleAndText(String language, String country, String text);

    @Query(value = "{'text': ?0}", fields = "{'_id':1, 'text':1}")
    Expression findOneBriefByText(String trimmedText);

    /**
     * https://docs.mongodb.com/manual/reference/operator/query/text/
     * <strong>Note:</strong> The field Expression.text must be full text indexed before running this query.
     *
     * @param expression
     * @return
     */
    @Query(value = "{$text: {$search : ?0}}", fields = "{'_id':1}")
    List<Expression> findIdsByContainText(String expression);

    @Query(value = "{$text: {$search : ?2}, 'locale.language': ?0, 'locale.country': ?1}")
    List<Expression> findByLocaleAndContainText(String language, String country, String expression);
}
