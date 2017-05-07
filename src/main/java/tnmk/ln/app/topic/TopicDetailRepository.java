package tnmk.ln.app.topic;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.topic.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Component
public class TopicDetailRepository {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    private Neo4jRepository neo4jRepository;

    public long countByTitleAndOwner(long ownerId, String title) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE n.`title`={p0} AND id(u)={p1} RETURN COUNT(n)", Topic.OWN_TOPIC);
        return neo4jRepository.count(queryString, title, ownerId);
    }

    public List<Topic> findByOwner(long ownerId) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE id(u)={p0} RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.findList(Topic.class, queryString, ownerId);
    }

    public List<Topic> findByOwnerAndTitle(long ownerId, String title) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE id(u)={p0} AND LOWER(u.title) = LOWER({p1}) RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.findList(Topic.class, queryString, ownerId, title);
    }

    public List<Topic> findByOwner(long ownerId, int depth) {
        String queryString = String.format("MATCH (t:Topic)"
                + " MATCH (t)<-[r:OWN_TOPIC]-(u:User) "
                + " OPTIONAL MATCH path=(t)-[*0..%s]-(n)"
                + " WITH path"
                + " RETURN path", depth);
        return neo4jRepository.findList(Topic.class, queryString, ownerId);
    }

    public Topic findOneByTitleAndOwner(long ownerId, String title) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) WHERE n.`title`={p0} AND id(u)={p1} RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.findOne(Topic.class, queryString, title, ownerId);
    }

    /**
     * NOTE: when changing any thing in expression, should recheck the query here.
     * Don't use findOne(id) default method of Neo4j because it can cause StackOverflow when there is an cycling references.
     *
     * @param topicId
     * @return
     */
    public Topic findOneDetailById(long topicId) {
        String queryString = " MATCH (n:Topic) WHERE ID(n) = {p0} "
                + " WITH n"
                + " MATCH p=(n)-[:HAS_VIDEOS | :HAS_LEXICAL_ENTRIES | :HAS_SENSES | :HAS_SENSE_GROUPS | :HAS_EXAMPLE | :OWN_EXPRESSION | :EXPRESSION_IN_LOCALE | :OWN_CATEGORY | :HAS_AUDIOS | :RELATE_TO_CATEGORY"
                + " | :HAS_MAIN_AUDIO | :LIKE | :TOPIC_IN_LOCALE | :HAS_EXPRESSION | :OWN_TOPIC | :SENSE_HAS_MAIN_PHOTO | :HAS_PHOTOS*0..5]-(m)"
//                + "-[r:IS_SYNONYMOUS_WITH | :IS_ANTONYMOUS_WITH | :FAMILY_WITH *0..1]->(l) "
                + " RETURN p ";
//        return neo4jRepository.queryForObject(Topic.class, queryString, topicId);
        return neo4jRepository.findOneById(Topic.class, queryString, topicId);
    }

    //TODO should remove digital asset???
    public int removeOneAndCompositions(long id) {
        String query = "MATCH (n:Topic) "
                + "where id(n)={p0} "
                + "optional match "
                + "(n)--(e:Expression) "
                + "optional match "
                + "(e)--(sg:SenseGroup) "
                + "optional match "
                + "(sg)--(s:Sense) "
                + "optional match "
                + "(s)--(ex:Example) "
                + "optional match "
                + "(ex)--(q:Question) detach delete n,e,sg,s,ex,q";
        return neo4jRepository.execute(query, id).getNodesDeleted();
    }

    public List<Topic> findByIdIn(List<Long> topicIds) {
        String queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-topics-by-ids.cql", topicIds);
        return neo4jRepository.findList(Topic.class, queryString, topicIds);
    }

    public List<Topic> findByOwnerAndContainTitleOrExpressionIds(Long ownerId, String title, List<String> expressionIds) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) "
                + " UNWIND n.expressionIds AS expressionId "
                + " WITH n, expressionId "
                + " WHERE id(u)={p0} AND ("
                + "     LOWER(n.title) CONTAINS LOWER({p1}) "
                + "     OR expressionId IN {p2}"
                + " )"
                + " RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.findList(Topic.class, queryString, ownerId, title, expressionIds);
    }

    public List<Topic> findByOwnerAndExpressionIds(Long ownerId, List<String> expressionIds) {
        String queryString = String.format("MATCH (n:Topic)<-[r:%s]-(u:User) "
                + " UNWIND n.expressionIds AS expressionId "
                + " WITH n, expressionId "
                + " WHERE id(u)={p0} AND ("
                + "     expressionId IN {p1}"
                + " )"
                + " RETURN n", Topic.OWN_TOPIC);
        return neo4jRepository.findList(Topic.class, queryString, ownerId, expressionIds);
    }
}
