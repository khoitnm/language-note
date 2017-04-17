package tnmk.ln.app.common.entity;

import org.neo4j.ogm.annotation.Transient;

/**
 * @author khoi.tran on 3/31/17.
 */
public interface Cleanable {
    @Transient
    boolean isEmpty();
}
