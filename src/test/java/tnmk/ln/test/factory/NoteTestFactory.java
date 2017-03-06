package tnmk.ln.test.factory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    public Note initNote(String title, String... topicTexts) {
        Note note = noteService.findOneByTitle(title);
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

        User user = userFactory.initDefaultUser();
        note.setOwner(user);
        note = noteRepository.save(note);
//        return noteService.createNote(userFactory.initDefaultUser(), note);
        return note;
    }
}
