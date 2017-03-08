package tnmk.ln.app.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.practice.QuestionService;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteAndOwnerRepository noteAndOwnerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @Transactional
    public Note saveNoteAndRelationships(User user, Note note) {
        note.setOwner(user);
        Set<Expression> expressionSet = note.getExpressions();
        topicService.saveIfNecessaryByTextAndOwner(user, note.getTopics());
        note = noteRepository.save(note);
        expressionSet.stream().forEach(expression -> questionService.createQuestionsIfNotExist(expression));
        return note;
    }

//    @Transactional
//    public Note updateNote(User user, Note note) {
//        note = noteRepository.save(note);
//        //TODO generate questions for new expression.senseGroup.sense.example
//        return note;
//    }

    /**
     * @param user
     * @return list of global notes and personal notes
     */
//    public List<Note> findViewableNodes(User user) {
//
//    }
//
//    public List<Note> findGlobalNotes(User user) {
//
//    }

//    public List<Note> findPersonalNotes(User user) {
//
//    }
    public List<Note> findAllNotes(User user) {
        return IterableUtil.toList(noteRepository.findAll());
    }

    public Note findNoteForEditing(User user, Long noteId) {
        Note note = noteRepository.findOne(noteId);
//        if (note != null) {
//            note.getExpressions()
//        }
        return note;
    }

    /**
     * @param expressionEditor
     * @param noteId
     * @param expression       if expression is new, it will be sent. Otherwise, it will be updated.
     */
    @Transactional
    public void addExpressionToNote(User expressionEditor, Long noteId, Expression expression) {
        boolean isNewExpression = expression.getId() == null;
        Note note = new Note();
        note.setId(noteId);
        note.setExpressions(SetUtil.constructSet(expression));
        expression.setOwner(expressionEditor);
        noteRepository.save(note);
        if (isNewExpression) {
            questionService.createQuestions(expression);
        }
    }

    private void generateQuestionsForExpression(User user, Expression expression) {

    }

    /**
     * This method only remove the relationship, the end node still exists in the DB.
     *
     * @param user
     * @param noteId
     * @param expressionId
     */
    public void removeExpressionRelationshipFromNote(User user, Long noteId, Long expressionId) {
        //TODO remove relationship(RELATIONSHIP_NAME, nodeId, rexpressionId);
    }

    public Note findOneByTitle(Long userId, String title) {
        return noteAndOwnerRepository.findOneByTitleAndOwner(userId, title);
    }
    public Note findDetailById(Long noteId) {
        return noteAndOwnerRepository.findOneDetailById(noteId);
    }
}
