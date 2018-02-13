package org.tnmk.ln.app.vocabulary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.app.vocabulary.entity.ExpressionItem;

@MongoRepoScanInclude
@Repository
public interface ExpressionItemRepository extends MongoRepository<ExpressionItem, String> {
}
