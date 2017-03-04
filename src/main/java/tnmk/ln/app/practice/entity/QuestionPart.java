package tnmk.ln.app.practice.entity;

import tnmk.ln.app.common.entity.BaseNeo4jEntity;

/**
 * @author khoi.tran on 3/4/17.
 */
public class QuestionPart extends BaseNeo4jEntity{
    private String text;
    private QuestionPartType questionPartType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionPartType getQuestionPartType() {
        return questionPartType;
    }

    public void setQuestionPartType(QuestionPartType questionPartType) {
        this.questionPartType = questionPartType;
    }
}
