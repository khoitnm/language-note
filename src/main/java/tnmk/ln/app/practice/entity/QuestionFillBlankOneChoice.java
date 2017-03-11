package tnmk.ln.app.practice.entity;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = "QuestionFillBlankOneChoice")
public class QuestionFillBlankOneChoice extends Question {
    private QuestionType questionType = QuestionType.FILL_BLANK_ONE_CHOICE;

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
