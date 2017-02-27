package tnmk.ln.app.practice.entity;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;
import tnmk.ln.app.common.entity.BaseEntity;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.note.entity.Topic;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
//@NodeEntity
public class Question extends BaseEntity {
    private QuestionType questionType;

    private Set<Sense> relatedSenses;
    private Set<Note> relatedNotes;
    private Set<Topic> relatedTopics;

    /**
     * Actually, this is the shortcut relationship between (question) -- (senses) -- (expressions)
     */
    private Set<Expression> relatedExpressions;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Set<Sense> getRelatedSenses() {
        return relatedSenses;
    }

    public void setRelatedSenses(Set<Sense> relatedSenses) {
        this.relatedSenses = relatedSenses;
    }

    public Set<Note> getRelatedNotes() {
        return relatedNotes;
    }

    public void setRelatedNotes(Set<Note> relatedNotes) {
        this.relatedNotes = relatedNotes;
    }

    public Set<Topic> getRelatedTopics() {
        return relatedTopics;
    }

    public void setRelatedTopics(Set<Topic> relatedTopics) {
        this.relatedTopics = relatedTopics;
    }
}
