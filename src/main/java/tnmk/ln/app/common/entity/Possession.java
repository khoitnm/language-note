package tnmk.ln.app.common.entity;

import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 */
public interface Possession extends Neo4jEntity {
    User getOwner();
}