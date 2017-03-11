package tnmk.ln.app.practice.entity;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = QuestionListening.LOGIC_NAME)
public class QuestionListening extends Question {
    public static final String LOGIC_NAME = "QuestionListening";

    private QuestionType questionType = QuestionType.LISTENING;

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
