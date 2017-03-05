package tnmk.ln.app.note;

import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.ExpressionFactory;
import tnmk.ln.app.note.entity.Note;

/**
 * @author khoi.tran on 3/4/17.
 */
public class NoteFactory {
    public static Note constructSchema() {
        Note note = new Note();
        note.setExpressions(SetUtil.constructSet(ExpressionFactory.constructSchema()));
        note.setTopics(SetUtil.constructSet(TopicFactory.constructSchema()));
        return note;
    }

}
