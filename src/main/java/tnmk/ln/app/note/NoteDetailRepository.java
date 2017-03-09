package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Component
public class NoteDetailRepository {
    @Autowired
    private Neo4jRepository neo4jRepository;

    public long countByTitleAndOwner(long ownerId, String title) {
        String queryString = String.format("MATCH (n:Note)<-[r:%s]-(u:User) WHERE n.`title`={p0} AND id(u)={p1} RETURN COUNT(n)", Note.OWN_NOTE);
        return neo4jRepository.count(queryString, title, ownerId);
    }

    public Note findOneByTitleAndOwner(long ownerId, String title) {
        String queryString = String.format("MATCH (n:Note)<-[r:%s]-(u:User) WHERE n.`title`={p0} AND id(u)={p1} RETURN n", Note.OWN_NOTE);
        return neo4jRepository.queryForObject(Note.class, queryString, title, ownerId);
    }

    public Note findOneDetailById(long noteId) {
        return neo4jRepository.queryOneDetail(Note.class, noteId);
    }

    //TODO should remove digital asset???
    public int removeOneAndCompositions(long id) {
        String query = "MATCH (n:Note) "
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
}
