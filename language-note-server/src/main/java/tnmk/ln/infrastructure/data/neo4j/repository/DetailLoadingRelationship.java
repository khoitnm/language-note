package tnmk.ln.infrastructure.data.neo4j.repository;

import java.util.Set;

/**
 * @author khoi.tran on 3/8/17.
 */
public class DetailLoadingRelationship {
    private Set<String> relationshipTypes;
    private int depth;

    public Set<String> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void setRelationshipTypes(Set<String> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
