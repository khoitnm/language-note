package tnmk.ln.app.dictionary;

/**
 * @author khoi.tran on 3/9/17.
 */
//@Component
//public class ExpressionDetailNeo4jRepository {
//    @Autowired
//    Neo4jRepository neo4jRepository;
//
//    public Expression findOneDetailById(long id) {
//        return neo4jRepository.findOneDetail(Expression.class, id);
//    }
//
//    public int removeExpressionAndCompositions(long expressionId) {
//        String query = "MATCH (e:Expression) "
//                + " where id(e)={p0} "
//                + " optional match "
//                + " (e)--(sg:SenseGroup) "
//                + " optional match "
//                + " (sg)--(s:Sense) "
//                + " optional match "
//                + " (s)--(ex:Example) "
//                + " detach delete e,sg,s,ex";
//        return neo4jRepository.execute(query, expressionId).getNodesDeleted();
//    }
//
//    public int removeSenseGroupAndCompositions(long senseGroupId) {
//        String query = " MATCH (sg:SenseGroup) "
//                + " WHERE id(sg) = {p0}"
//                + " optional match "
//                + " (sg)--(s:Sense) "
//                + " optional match "
//                + " (s)--(ex:Example) "
//                + " detach delete sg,s,ex";
//        return neo4jRepository.execute(query, senseGroupId).getNodesDeleted();
//    }
//
//    public int removeSenseAndCompositions(long senseId) {
//        String query = " MATCH (s:Sense) "
//                + " WHERE id(s) = {p0}"
//                + " optional match "
//                + " (s)--(ex:Example) "
//                + " detach delete s,ex";
//        return neo4jRepository.execute(query, senseId).getNodesDeleted();
//    }
//
//    public int removeExampleAndCompositions(long exampleId) {
//        String query = " MATCH (ex:Example) "
//                + " WHERE id(ex) = {p0}"
//                + " detach delete ex";
//        return neo4jRepository.execute(query, exampleId).getNodesDeleted();
//    }
//
//    public int detachPhotoFromSense(Long photoId) {
//        String query = " MATCH (p:Photo)-[r]-(s:Sense) "
//                + " WHERE id(p) = {p0}"
//                + " delete r";
//        return neo4jRepository.execute(query, photoId).getNodesDeleted();
//    }
//
//    public Expression findOneDetailByText(String trimmedText) {
//        String queryString = " MATCH (n) WHERE LOWER(n.text) = LOWER({p0}) "
//                + " WITH n "
//                + " MATCH p=(n)-[:HAS_VIDEOS | :HAS_LEXICAL_ENTRIES | :HAS_SENSES | :HAS_SENSE_GROUPS | :HAS_EXAMPLE | :OWN_EXPRESSION | :EXPRESSION_IN_LOCALE | :OWN_CATEGORY | :HAS_AUDIOS"
//                + " | :HAS_MAIN_AUDIO | :LIKE | :OWN_TOPIC | :SENSE_HAS_MAIN_PHOTO | :HAS_PHOTOS*0..5]-(m)"
////                + "-[r:IS_SYNONYMOUS_WITH | :IS_ANTONYMOUS_WITH | :FAMILY_WITH *0..1]->(l) "
//                + " RETURN p ";
//        return neo4jRepository.findOne(Expression.class, queryString, trimmedText);
//    }
//
//    public Expression findOneBriefByText(String trimmedText) {
//        String queryString = " MATCH (n) WHERE LOWER(n.text) = LOWER({p0}) RETURN n ";
//        return neo4jRepository.findOne(Expression.class, queryString, trimmedText);
//    }
//}
