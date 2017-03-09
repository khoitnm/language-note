package tnmk.ln.test.neo4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.note.NoteDetailRepository;
import tnmk.ln.app.note.NoteRepository;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.test.BaseTest;

/**
 * @author khoi.tran on 3/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteTest extends BaseTest {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteDetailRepository noteAndOwnerRepository;

    @Test
    public void test() {
        Note note = noteAndOwnerRepository.findOneDetailById(150l);
        LOGGER.info(ObjectMapperUtil.toJson(new ObjectMapper(), note));
    }
}
