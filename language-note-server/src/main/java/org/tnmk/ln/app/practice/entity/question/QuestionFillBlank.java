package org.tnmk.ln.app.practice.entity.question;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = QuestionFillBlank.LOGIC_NAME)
public class QuestionFillBlank extends Question {
    public static final String LOGIC_NAME = "QuestionFillBlank";

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
