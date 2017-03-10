package tnmk.ln.test.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.note.NoteFactory;
import tnmk.ln.app.note.NoteRepository;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class NoteTestFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(NoteTestFactory.class);
    @Autowired
    private UserTestFactory userFactory;

    @Autowired
    private TopicTestFactory topicTestFactory;

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    public static Note constructNote(String title, String... topicTexts) {
        Note note = NoteFactory.constructSchema();
        note.setTitle(title);
        Set<Topic> topics = new HashSet<>();
        for (String topicText : topicTexts) {
            Topic topic = TopicTestFactory.construct(topicText);
            topics.add(topic);
        }
        note.setTopics(topics);
        return note;
    }

    @Transactional
    public Note initNote(User owner, String title, String... topicTexts) {
        Note note = noteService.findOneByTitle(owner.getId(), title);
        if (note == null) {
            note = new Note();
            if (StringUtils.isNotBlank(title)) {
                note.setTitle(title);
            }
        }
        Set<Topic> topics = new HashSet<>();
        for (String topicText : topicTexts) {
            Topic topic = topicTestFactory.initTopic(topicText);
            topics.add(topic);
        }
        note.setTopics(topics);
        note.setOwner(owner);
        note = noteRepository.save(note);
        note = noteService.findDetailById(note.getId());
        return note;
    }

    /**
     * @param owner
     * @param noteTitle
     * @return note with 2 topics and 3 expressions
     */
    public Note createNoteWithDefaultRelationships(User owner, String noteTitle) {
        Note note = NoteTestFactory.constructNote(noteTitle, "test_topic1", "test_topic2");
        note.setExpressions(SetUtil.constructSet(
                ExpressionTestFactory.constructWord("test_expression1")
                , ExpressionTestFactory.constructWord("test_expression2")
                , ExpressionTestFactory.constructWord("test_expression3")
        ));
        note = noteService.saveNoteAndRelationships(owner, note);
        LOGGER.debug("Created note: " + note);
        return note;
    }
}
