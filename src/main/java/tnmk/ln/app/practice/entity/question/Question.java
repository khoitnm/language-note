package tnmk.ln.app.practice.entity.question;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Question")
public class Question extends BaseNeo4jEntity implements Cleanable {
    public static final String FROM_EXAMPLE = "FROM_EXAMPLE";
    public static final String FROM_SENSE = "FROM_SENSE";
    public static final String FROM_EXPRESSION = "FROM_EXPRESSION";
    public static final String HAS_QUESTION_PARTS = "HAS_QUESTION_PARTS";

    private String text;

    @Relationship(type = HAS_QUESTION_PARTS, direction = Relationship.OUTGOING)
    private List<QuestionPart> questionParts;

    private String fromExampleId;

    private String fromSenseId;

    @NotNull
    private String fromExpressionId;

    private QuestionType questionType;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<QuestionPart> getQuestionParts() {
        return questionParts;
    }

    public void setQuestionParts(List<QuestionPart> questionParts) {
        this.questionParts = questionParts;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromExampleId() {
        return fromExampleId;
    }

    public void setFromExampleId(String fromExampleId) {
        this.fromExampleId = fromExampleId;
    }

    public String getFromSenseId() {
        return fromSenseId;
    }

    public void setFromSenseId(String fromSenseId) {
        this.fromSenseId = fromSenseId;
    }

    public String getFromExpressionId() {
        return fromExpressionId;
    }

    public void setFromExpressionId(String fromExpressionId) {
        this.fromExpressionId = fromExpressionId;
    }
}
