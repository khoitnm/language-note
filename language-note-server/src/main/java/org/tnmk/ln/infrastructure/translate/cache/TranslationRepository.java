package org.tnmk.ln.infrastructure.translate.cache;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;

/**
 * @author khoi.tran on 2/1/17.
 */
@MongoRepoScanInclude
@Repository
public interface TranslationRepository extends MongoRepository<Translation, String> {
    Translation findOneBySourceLanguageAndDestLanguageAndSourceText(String sourceLanguage, String destLanguage, String sourceText);
}
