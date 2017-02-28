package tnmk.ln.app.note;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.note.entity.Note;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.common.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;
import tnmk.ln.app.note.entity.Note;

/**
 * @author khoi.tran on 2/26/17.
 */
@Neo4jRepoScanInclude
public interface NoteRepository extends GraphRepository<Note> {
}
