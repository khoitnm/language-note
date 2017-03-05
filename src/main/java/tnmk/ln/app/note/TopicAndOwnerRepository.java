package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Component
public class TopicAndOwnerRepository {
    @Autowired
    private Neo4jRepository neo4jRepository;

    public long countByTextAndOwner(String topicText, long ownerId) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE n.`text`={p0} AND id(u)={p1} RETURN COUNT(n)", Topic.OWN_TOPIC);
        return neo4jRepository.count(queryString, topicText, ownerId);
    }

    public Topic findOneByTextAndOwner(String topicText, long ownerId) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE n.`text`={p0} AND id(u)={p1} RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.queryForObject(Topic.class, queryString, topicText, ownerId);
    }
}
