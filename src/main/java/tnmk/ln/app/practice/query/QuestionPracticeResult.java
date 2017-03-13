package tnmk.ln.app.practice.query;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionSet;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity(label = "QuestionPracticeResult")
public class QuestionPracticeResult extends PracticeResult {
    public static final String RESULT_OF_QUESTION = "RESULT_OF_QUESTION";
    public static final String RESULT_OF_QUESTION_SET = "RESULT_OF_QUESTION_SET";

    @Relationship(type = RESULT_OF_QUESTION, direction = Relationship.OUTGOING)
    private Question question;
    @Relationship(type = RESULT_OF_QUESTION_SET, direction = Relationship.OUTGOING)
    private QuestionSet questionSet;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionSet getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;
    }
}
