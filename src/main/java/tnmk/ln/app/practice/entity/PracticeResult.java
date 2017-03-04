package tnmk.ln.app.practice.entity;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.mongodb.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity
public class PracticeResult extends BaseNeo4jEntity {
    private User owner;
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
