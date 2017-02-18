package tnmk.ln.infrastructure.tts.cache;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author khoi.tran on 2/2/17.
 */
@Repository
public interface TtsItemRepository extends MongoRepository<TtsItem, String> {

    TtsItem findOneByLocaleAndText(String locale, String text);
}
