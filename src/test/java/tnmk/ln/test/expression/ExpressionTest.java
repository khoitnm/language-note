package tnmk.ln.test.expression;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.NoteTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class ExpressionTest extends BaseTest {
    @Autowired
    NoteService noteService;

    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    NoteTestFactory noteTestFactory;

    private static final User USER = UserTestFactory.constructUser(1l);

    @Test
    public void createNote() {
        Note note = new Note();
        note = noteService.saveNoteAndRelationships(USER, note);
        Assert.assertEquals(Note.TITLE_DEFAULT, note);
    }

    @Test
    public void addExpressionToNote() {
        User user = userTestFactory.initDefaultUser();

        Note note = noteTestFactory.initNote("test_" + System.currentTimeMillis(), "topic" + System.currentTimeMillis(), "topic" + System.currentTimeMillis());
        Expression expression = new Expression();
        noteService.addExpressionToNote(user, note.getId(), expression);
    }
}
