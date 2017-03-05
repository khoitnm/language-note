package tnmk.ln.app.note.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Topic")
public class Topic extends BaseNeo4jEntity {
    public static final String OWN_TOPIC = "OWN_TOPIC";
    /**
     * Different users can have different topics with the same text, but one user cannot have 2 topics with the same text.
     */
    @Relationship(type = OWN_TOPIC, direction = Relationship.INCOMING)
    private User owner;
    private String text;
    private Set<Topic> children;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Topic> getChildren() {
        return children;
    }

    public void setChildren(Set<Topic> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
