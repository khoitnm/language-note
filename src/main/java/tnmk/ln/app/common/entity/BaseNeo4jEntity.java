package tnmk.ln.app.common.entity;

import org.neo4j.ogm.annotation.GraphId;

/**
 * @author khoi.tran on 1/25/17.
 */
public class BaseNeo4jEntity {
    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Date getCreatedDateTime() {
//        return createdDateTime;
//    }
//
//    public void setCreatedDateTime(Date createdDateTime) {
//        this.createdDateTime = createdDateTime;
//    }
//
//    public Date getUpdatedDateTime() {
//        return updatedDateTime;
//    }
//
//    public void setUpdatedDateTime(Date updatedDateTime) {
//        this.updatedDateTime = updatedDateTime;
//    }
}
