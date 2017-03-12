package tnmk.ln.app.practice.entity.practiceresult;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity(label = "PracticeResult")
public class PracticeResult extends BaseNeo4jEntity {
    @Relationship(type = "PRACTICE", direction = Relationship.INCOMING)
    private User owner;
    @Relationship(type = "HAS_POINTS", direction = Relationship.INCOMING)
    private List<AnswerPoint> answers;
    private double latestAnswerPoints;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<AnswerPoint> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerPoint> answers) {
        this.answers = answers;
    }

    public double getLatestAnswerPoints() {
        return latestAnswerPoints;
    }

    public void setLatestAnswerPoints(double latestAnswerPoints) {
        this.latestAnswerPoints = latestAnswerPoints;
    }

}
