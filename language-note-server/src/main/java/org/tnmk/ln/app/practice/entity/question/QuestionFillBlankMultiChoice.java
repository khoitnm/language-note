package org.tnmk.ln.app.practice.entity.question;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = QuestionFillBlankMultiChoice.LOGIC_NAME)
public class QuestionFillBlankMultiChoice extends Question {
    public static final String LOGIC_NAME = "QuestionFillBlankMultiChoice";

    private QuestionType questionType = QuestionType.FILL_BLANK_MULTI_CHOICES;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
