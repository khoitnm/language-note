package tnmk.ln.app.topic;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.topic.entity.Category;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.topic.entity.Category;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface CategoryRepository extends GraphRepository<Category> {
    Category findOneByText(String categoryText);

    /**
     * Spring Data doesn't run with the @Query, it uses its own generated query
     *
     * @param text
     * @param userId
     * @return
     */
    @Deprecated
    @Query(value = "MATCH (n:`Category`)<-[r:" + Category.OWN_CATEGORY + "]-(u:User) WHERE n.`text`={text} AND id(u)={userId}", count = true)
    Long countByTextAndOwnerId(String text, Long userId);
}
