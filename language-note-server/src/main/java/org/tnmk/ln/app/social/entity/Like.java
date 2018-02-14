package org.tnmk.ln.app.social.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.tnmk.ln.app.common.entity.BaseNeo4jEntity;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 2/28/17.
 */
@NodeEntity
public class Like extends BaseNeo4jEntity {
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
