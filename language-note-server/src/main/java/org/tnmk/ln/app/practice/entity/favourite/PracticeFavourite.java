package org.tnmk.ln.app.practice.entity.favourite;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.tnmk.ln.app.common.entity.BaseNeo4jEntity;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 4/30/17.
 */
@NodeEntity(label = "PracticeFavourite")
public class PracticeFavourite extends BaseNeo4jEntity {
    private static final String FAVOURITE_EXPRESSION_OWNER = "FAVOURITE_EXPRESSION_OWNER";

    @Index
    private String expressionId;
    private int favourite;
    @Relationship(type = FAVOURITE_EXPRESSION_OWNER, direction = Relationship.INCOMING)
    @Index
    private User owner;

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
