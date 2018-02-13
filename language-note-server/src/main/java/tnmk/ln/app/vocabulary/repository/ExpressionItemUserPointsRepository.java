package tnmk.ln.app.vocabulary.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import tnmk.ln.app.vocabulary.entity.ExpressionItem;
import tnmk.ln.app.vocabulary.entity.UserPoint;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@MongoRepoScanInclude
@Repository
public class ExpressionItemUserPointsRepository {
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    public int updateUserPoints(String userId, List<ExpressionItem> expressionItems) {
        List<Pair<Query, Update>> updateOps = new ArrayList<>();
        for (ExpressionItem expressionItem : expressionItems) {
            Query query = createQueryById(expressionItem.getId());
            UserPoint userPoint = expressionItem.getUserPoints().getUserPoint(userId);
            DBObject dbObject = toDBObject(userPoint);
            Update update = Update.update("userPoints." + userId, dbObject);
            Pair<Query, Update> pair = Pair.of(query, update);
            updateOps.add(pair);
        }

        return mongoOperations.bulkOps(BulkOperations.BulkMode.ORDERED, ExpressionItem.class, ExpressionItem.class.getAnnotation(Document.class).collection()).updateOne(updateOps).execute().getModifiedCount();

    }

    public int updateFavourite(String userId, String expressionId, int favourite) {
        Query query = createQueryById(expressionId);
        Update update = Update.update("userPoints." + userId + ".favourite", favourite);
        return mongoOperations.updateMulti(query, update, ExpressionItem.class).getN();
    }

    private DBObject toDBObject(Object object) {
        DBObject dbObject = new BasicDBObject();
        mappingMongoConverter.write(object, dbObject);
        return dbObject;
    }

    private Query createQueryById(String id) {
        return new Query().addCriteria(where("_id").is(new ObjectId(id)));
    }
}
