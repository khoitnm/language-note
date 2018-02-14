package org.tnmk.ln.app.common.entity;

import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 */
public interface Possession  {
    User getOwner();
}
