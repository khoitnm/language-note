package org.tnmk.ln.app.practice.entity.question;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = QuestionExpressionRecall.LOGIC_NAME)
public class QuestionExpressionRecall extends Question {
    public static final String LOGIC_NAME = "QuestionExpressionRecall";

    private QuestionType questionType = QuestionType.EXPRESSION_RECALL;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
