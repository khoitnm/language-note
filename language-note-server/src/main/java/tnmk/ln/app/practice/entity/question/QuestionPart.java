package tnmk.ln.app.practice.entity.question;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.Transient;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;

/**
 * @author khoi.tran on 3/4/17.
 */
public class QuestionPart extends BaseNeo4jEntity implements Cleanable {
    private String lemma;
    private String text;
    private QuestionPartType questionPartType;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", text, questionPartType);
    }

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

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
}
