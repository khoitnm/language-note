package org.tnmk.ln.app.practice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.app.practice.entity.question.QuestionParts;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@MongoRepoScanInclude
public interface QuestionPartsRepository extends MongoRepository<QuestionParts, String> {
    @Query(value = "{'_id': {$in: ?0}}")
    List<QuestionParts> findByIdIn(List<String> questionPartsIds);

}
