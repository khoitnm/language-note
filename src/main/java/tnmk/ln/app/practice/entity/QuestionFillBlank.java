package tnmk.ln.app.practice.entity;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = "QuestionFillBlank")
public class QuestionFillBlank extends Question {
    private QuestionType questionType = QuestionType.FILL_BLANK;

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
