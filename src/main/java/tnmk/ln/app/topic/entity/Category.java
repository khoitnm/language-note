package tnmk.ln.app.topic.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Category")
public class Category extends BaseNeo4jEntity {
    public static final String OWN_CATEGORY = "OWN_CATEGORY";
    /**
     * Different users can have different categorys with the same text, but one user cannot have 2 categorys with the same text.
     */
    @Relationship(type = OWN_CATEGORY, direction = Relationship.INCOMING)
    private User owner;
    private String text;
    private Set<Category> children;

    @Override
    public String toString() {
        return String.format("Category{%s, %s}", super.getId(), text);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}