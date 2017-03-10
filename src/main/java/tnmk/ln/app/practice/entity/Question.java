package tnmk.ln.app.practice.entity;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;

import java.util.List;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity
public class Question extends BaseNeo4jEntity {
    public static final String FROM_EXAMPLE = "FROM_EXAMPLE";
    public static final String FROM_SENSE = "FROM_SENSE";
    public static final String FROM_EXPRESSION = "FROM_EXPRESSION";

    private QuestionType questionType;
    private List<QuestionPart> questionParts;

    @Relationship(type = FROM_EXAMPLE, direction = Relationship.OUTGOING)
    private Example fromExample;
    @Relationship(type = FROM_SENSE, direction = Relationship.OUTGOING)
    private Sense fromSense;
    @Relationship(type = FROM_EXPRESSION, direction = Relationship.OUTGOING)
    private Expression fromExpression;

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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Sense getFromSense() {
        return fromSense;
    }

    public void setFromSense(Sense fromSense) {
        this.fromSense = fromSense;
    }
}
