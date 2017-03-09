package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Note;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface NoteRepository extends GraphRepository<Note> {
    List<Note> findByOwnerId(Long ownerId);
}
