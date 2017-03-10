package tnmk.ln.app.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.practice.QuestionGenerationService;
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
    private NoteDetailRepository noteAndOwnerRepository;

    @Autowired
    private QuestionGenerationService questionService;

    @Autowired
    private TopicService topicService;

    @Transactional
    public Note saveNoteAndRelationships(User user, Note note) {
        note.setOwner(user);
        Set<Expression> expressionSet = note.getExpressions();
        topicService.saveIfNecessaryByTextAndOwner(user, note.getTopics());
        if (expressionSet != null) {
            expressionSet.stream().forEach(expression -> expression.setOwner(user));
        }
        note = noteRepository.save(note);
        if (expressionSet != null) {
            expressionSet.stream().forEach(expression -> questionService.createQuestionsIfNotExist(expression));
        }
        return note;
    }

    public void deleteNoteAndExpressions(User user, Long noteId) {
        Note note = findDetailById(noteId);
        if (note.getExpressions() != null) {
            for (Expression expression : note.getExpressions()) {

            }
        }
    }

    public List<Note> findByOwnerId(User user) {
        return noteRepository.findByOwnerId(user.getId());
    }

    public List<Note> findAll() {
        return IterableUtil.toList(noteRepository.findAll());
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
