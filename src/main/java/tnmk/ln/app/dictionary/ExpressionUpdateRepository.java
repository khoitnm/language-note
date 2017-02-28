package tnmk.ln.app.dictionary;

import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.neo4j.repository.OgmRepository;
import tnmk.ln.app.dictionary.entity.Expression;

/**
 * @author khoi.tran on 2/28/17.
 */
@Repository
public class ExpressionUpdateRepository extends OgmRepository<Expression> {
//    @Autowired
//    private Session session;

//    @Transactional
//    public void save(Expression expression) {
////        session.detachNodeEntity(expression.getId());
//        detach(expression);
//        session.save(expression);
//    }
//
//    @Transactional
//    public void updateIncludeRelationships(Expression expression) {
//        detach(expression);
//        session.save(expression);
//    }
//
//    @Transactional
//    public void detach(Expression expression) {
//        String queryString = "MATCH (a:Expression)-[rel:IS_SYNONYMOUS_WITH]-(b) WHERE id(a)={id} DELETE rel";
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", expression.getId());
//        Result result = session.query(queryString, params);
//        result.queryStatistics().getRelationshipsDeleted();
////        session.detachNodeEntity(expression.getId());
//    }
//
//    @Transactional
//    public void detachOutComing(Expression expression) {
//        String queryString = "MATCH (a:Expression)-[rel:IS_SYNONYMOUS_WITH]->(b) WHERE id(a)={id} DELETE rel";
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", expression.getId());
//        Result result = session.query(queryString, params);
//        result.queryStatistics().getRelationshipsDeleted();
////        session.detachNodeEntity(expression.getId());
//    }
}
