package org.tnmk.ln.app.datastructure;

import org.neo4j.ogm.annotation.NodeEntity;
import org.tnmk.ln.app.common.entity.BaseNeo4jEntity;

import java.util.List;

/**
 * @author khoi.tran on 3/30/17.
 */
@NodeEntity
public class NodeWithProps extends BaseNeo4jEntity {
    private List<Float> points;

    public List<Float> getPoints() {
        return points;
    }

    public void setPoints(List<Float> points) {
        this.points = points;
    }
}
