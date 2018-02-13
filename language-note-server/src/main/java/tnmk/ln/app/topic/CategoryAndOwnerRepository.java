package tnmk.ln.app.topic;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.topic.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.topic.entity.Category;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Component
public class CategoryAndOwnerRepository {
    @Autowired
    private Neo4jRepository neo4jRepository;

    public long countByTextAndOwner(String categoryText, long ownerId) {
        String queryString = String.format("MATCH (n:Category)<-[r:%s]-(u:User) WHERE n.`text`={p0} AND id(u)={p1} RETURN COUNT(n)", Category.OWN_CATEGORY);
        return neo4jRepository.count(queryString, categoryText, ownerId);
    }

    public Category findOneByTextAndOwner(String categoryText, long ownerId) {
        String queryString = String.format("MATCH (n:Category)<-[r:%s]-(u:User) WHERE n.`text`={p0} AND id(u)={p1} RETURN n", Category.OWN_CATEGORY);
        return neo4jRepository.findOne(Category.class, queryString, categoryText, ownerId);
    }
}
