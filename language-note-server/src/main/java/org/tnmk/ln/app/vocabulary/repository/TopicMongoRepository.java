package org.tnmk.ln.app.vocabulary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.app.vocabulary.entity.Topic;

/**
 * I must rename it to TopicMongoRepository to avoid naming conflict with TopicRepository of Neo4j.
 */
@MongoRepoScanInclude
@Repository
public interface TopicMongoRepository extends MongoRepository<Topic, String> {
    Topic findOneByName(String name);
}
