package tnmk.ln.app.practice.entity.result;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Possession;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity(label = "PracticeResult")
public abstract class BasePracticeResult extends BaseNeo4jEntity implements Possession {
    public static final String PRACTICE = "PRACTICE";

    @Relationship(type = PRACTICE, direction = Relationship.INCOMING)
    private User owner;

    private List<Float> answers;
    /**
     * We use sum, not use average because 0.9/1 means that expression was tested only one time, while (0.9 + 0.1)/2 means that expression was tested two times. So we still prioritize for the expression with least testing times first.
     */
    private double sumLatestAnswerPoint;

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public double getSumLatestAnswerPoint() {
        return sumLatestAnswerPoint;
    }

    public void setSumLatestAnswerPoint(double sumLatestAnswerPoint) {
        this.sumLatestAnswerPoint = sumLatestAnswerPoint;
    }

    public List<Float> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Float> answers) {
        this.answers = answers;
    }
}
