package tnmk.ln.app.practice.entity;

//import org.neo4j.ogm.annotation.NodeEntity; import org.neo4j.ogm.annotation.Property;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity
public class AnswerPoint extends BaseNeo4jEntity {

    private int maxPoints;
    private int correctPoints;

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getCorrectPoints() {
        return correctPoints;
    }

    public void setCorrectPoints(int correctPoints) {
        this.correctPoints = correctPoints;
    }
}
