package tnmk.ln.app.common.entity;

import org.neo4j.ogm.annotation.Transient;

/**
 * @author khoi.tran on 3/31/17.
 */
public interface Cleanable extends Neo4jEntity {
    @Transient
    boolean isEmpty();
}
