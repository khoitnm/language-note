package tnmk.ln.app.social.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.user.entity.Account;

/**
 * @author khoi.tran on 2/28/17.
 */
@NodeEntity
public class Like extends BaseNeo4jEntity {
    private Account owner;

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
