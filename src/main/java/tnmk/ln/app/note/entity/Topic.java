package tnmk.ln.app.note.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.entity.User;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity
public class Topic extends BaseNeo4jEntity {
    private User owner;
//    private List<Topic> children;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
//
//    public List<Topic> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<Topic> children) {
//        this.children = children;
//    }
}
