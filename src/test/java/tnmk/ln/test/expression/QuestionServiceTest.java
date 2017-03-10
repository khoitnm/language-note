package tnmk.ln.test.expression;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.DateTimeUtil;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.note.NoteRepository;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.TopicAndOwnerRepository;
import tnmk.ln.app.note.TopicRepository;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.practice.QuestionService;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.NoteAssert;
import tnmk.ln.test.factory.NoteTestFactory;
import tnmk.ln.test.factory.UserTestFactory;

import java.util.List;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class QuestionServiceTest extends BaseTest {
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
    @Autowired
    ExpressionService expressionService;

    @Autowired
    QuestionService questionService;

    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void question() {
        User owner = defaultUser;
        String noteTitle = "test_note_" + DateTimeUtil.formatLocalDateTimeForFilePath();
        Note note = noteTestFactory.createNoteWithDefaultRelationships(owner, noteTitle);
        NoteAssert.assertExpressions(note, owner, 3);

        List<Question> questions = questionService.loadQuestionsByNotes(owner, note.getId());
        LOGGER.info("Questions: \n" + ObjectMapperUtil.toStringMultiLineForEachElement(questions));
        Assert.assertTrue(questions.size() > 3);
    }

}
