package tnmk.ln.test.expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.TopicAndOwnerRepository;
import tnmk.ln.app.note.TopicRepository;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.ExpressionTestFactory;
import tnmk.ln.test.factory.NoteAssert;
import tnmk.ln.test.factory.NoteTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class NoteServiceTest extends BaseTest {
    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    NoteTestFactory noteTestFactory;

    @Autowired
    NoteService noteService;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicAndOwnerRepository topicCountRepository;
    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void createNoteWithoutExpression() {
        Note note = NoteTestFactory.constructNote("test_note_no_expression", "test_topic1", "test_topic2");
        note = noteService.saveNoteAndRelationships(defaultUser, note);

        NoteAssert.assertTopics(note, defaultUser, 2);
    }

    @Test
    public void createNoteWithNewExpression() {
        Note note = NoteTestFactory.constructNote("test_note_no_expression", "test_topic1", "test_topic2");
        note.setExpressions(SetUtil.constructSet(
                ExpressionTestFactory.constructWord("test_expression1")
                , ExpressionTestFactory.constructWord("test_expression2")
                , ExpressionTestFactory.constructWord("test_expression3")
        ));
        Note savedNote = noteService.saveNoteAndRelationships(defaultUser, note);
        NoteAssert.assertTopics(savedNote, defaultUser, 2);
        NoteAssert.assertExpressions(savedNote, defaultUser, 3);
    }
}
