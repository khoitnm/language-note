package tnmk.ln.app.vocabulary.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.repositoriesfilter.MongoRepoScanInclude;
import tnmk.ln.app.vocabulary.entity.ExpressionItem;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@MongoRepoScanInclude
@Repository
public class ExpressionItemRemoveRepository {
    @Autowired
    private MongoOperations mongoOperations;

    public int removeByLessonIds(String... lessonIds) {
        Query query = new Query().addCriteria(where("lessonIds").in(lessonIds));
        return mongoOperations.remove(query, ExpressionItem.class).getN();
    }
}
