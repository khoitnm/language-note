package tnmk.ln.app.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.practice.QuestionService;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public Note createNote(User user, Note note) {
        note.setOwner(user);
        Set<Expression> expressionSet = note.getExpressions();
        note = noteRepository.save(note);
//      TODO  expressionSet.stream().forEach(expression -> questionService.constructQuestionsForExpression(expression));
        return note;
    }

}
