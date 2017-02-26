package tnmk.ln.app.common.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.springframework.data.annotation.Id;

/**
 * @author khoi.tran on 1/25/17.
 */
public class BaseEntity {
    @Id
    @GraphId
    private String id;

//    @DateLong
//    private Date createdDateTime;
//    @DateLong
//    private Date updatedDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
