package org.tnmk.ln.infrastructure.tts.cache;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;

/**
 * @author khoi.tran on 2/2/17.
 */
@MongoRepoScanInclude
@Repository
public interface TtsItemRepository extends MongoRepository<TtsItem, String> {

    TtsItem findOneByLocaleAndText(String locale, String text);
}
