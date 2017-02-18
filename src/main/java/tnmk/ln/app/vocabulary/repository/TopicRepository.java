package tnmk.ln.app.vocabulary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tnmk.ln.app.vocabulary.entity.Topic;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    Topic findOneByName(String name);
}
