package org.tnmk.ln.infrastructure.dictionary.oxford;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;

import java.util.List;

/**
 * @author khoi.tran on 2/24/17.
 */
@MongoRepoScanInclude
@Repository
public interface OxfordAudioRepositories extends MongoRepository<OxfordAudio, String> {
    List<OxfordAudio> findByLanguageAndWord(String sourceLanguage, String word);

    OxfordAudio findByOriginalUrl(String audioUrl);
}
