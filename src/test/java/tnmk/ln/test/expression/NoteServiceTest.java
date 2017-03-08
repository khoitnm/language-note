package tnmk.ln.test.expression;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.DateTimeUtil;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.note.NoteRepository;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.TopicAndOwnerRepository;
import tnmk.ln.app.note.TopicFactory;
import tnmk.ln.app.note.TopicRepository;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.note.entity.Topic;
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
    NoteRepository noteRepository;
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

        NoteAssert.assertExistTopic(note, defaultUser, 2);
    }

    @Test
    public void createNoteWithNewExpression() {
        User owner = defaultUser;
        Note note = NoteTestFactory.constructNote("test_note_with_expression", "test_topic1", "test_topic2");
        note.setExpressions(SetUtil.constructSet(
                ExpressionTestFactory.constructWord("test_expression1")
                , ExpressionTestFactory.constructWord("test_expression2")
                , ExpressionTestFactory.constructWord("test_expression3")
        ));
        Note savedNote = noteService.saveNoteAndRelationships(owner, note);
        LOGGER.debug("New created note: " + savedNote.getId());
        NoteAssert.assertExistTopic(savedNote, owner, 2);
        NoteAssert.assertExpressions(savedNote, owner, 3);
    }

    @Test
    public void updateNoteWithNewExpression() {
        User owner = defaultUser;
        String noteTitle = "test_note_with_expression";
        Note note = initExistingNoteOfUser(owner, noteTitle);

        //UPDATE & ADD TOPIC ------------------------------------
        int numOldTopics = note.getTopics().size();
        //Update old topic
        Topic oldTopic = note.getTopics().iterator().next();
        String oldTopicText = oldTopic.getText();
        String newTopicText = "test_updated_topic_" + DateTimeUtil.formatLocalDateTimeForFilePath();
        oldTopic.setText(newTopicText);
        //Add new topic
        Topic newTopic = TopicFactory.constructSchema();
        note.getTopics().add(newTopic);
        newTopic.setText("test_topic_" + DateTimeUtil.formatLocalDateTimeForFilePath());

        //UPDATE & ADD EXPRESSION ------------------------------------
        int numOldExpressions = (note.getExpressions() != null) ? note.getExpressions().size() : 0;
        //Update expression

        Note savedNote = noteService.saveNoteAndRelationships(owner, note);
        //TODO it may load only one level relationships
        NoteAssert.assertExistTopic(savedNote, oldTopicText, false);
        NoteAssert.assertExistTopic(savedNote, newTopicText, true);
        NoteAssert.assertExistTopic(savedNote, owner, numOldTopics + 1);
        NoteAssert.assertExpressions(savedNote, owner, 3);
    }

    private Note initExistingNoteOfUser(User owner, String noteTitle) {
        return noteTestFactory.initNote(owner, noteTitle, "test_topic1", "test_topic2");
    }

    @Test
    public void testLoadNote() {
        Note note = noteRepository.findOne(260l);
        LOGGER.info(ObjectMapperUtil.toJson(new ObjectMapper(), note));
    }
}
