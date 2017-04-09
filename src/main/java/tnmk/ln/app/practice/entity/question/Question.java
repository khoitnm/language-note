package tnmk.ln.app.practice.entity.question;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Question")
public abstract class Question extends BaseNeo4jEntity implements Cleanable {
    public static final String FROM_EXAMPLE = "FROM_EXAMPLE";
    public static final String FROM_SENSE = "FROM_SENSE";
    public static final String FROM_EXPRESSION = "FROM_EXPRESSION";
    public static final String HAS_QUESTION_PARTS = "HAS_QUESTION_PARTS";

    private String text;

    @Relationship(type = HAS_QUESTION_PARTS, direction = Relationship.OUTGOING)
    private List<QuestionPart> questionParts;

    @Relationship(type = FROM_EXAMPLE, direction = Relationship.OUTGOING)
    private Example fromExample;

    @Relationship(type = FROM_SENSE, direction = Relationship.OUTGOING)
    private Sense fromSense;

    @NotNull
    @Relationship(type = FROM_EXPRESSION, direction = Relationship.OUTGOING)
    private Expression fromExpression;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

    public abstract QuestionType getQuestionType();

    public abstract void setQuestionType(QuestionType questionType);

    public List<QuestionPart> getQuestionParts() {
        return questionParts;
    }

    public void setQuestionParts(List<QuestionPart> questionParts) {
        this.questionParts = questionParts;
    }

    public Example getFromExample() {
        return fromExample;
    }

    public void setFromExample(Example fromExample) {
        this.fromExample = fromExample;
    }

    public Expression getFromExpression() {
        return fromExpression;
    }

    public void setFromExpression(Expression fromExpression) {
        this.fromExpression = fromExpression;
    }

    public Sense getFromSense() {
        return fromSense;
    }

    public void setFromSense(Sense fromSense) {
        this.fromSense = fromSense;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
