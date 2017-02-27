package tnmk.ln.app.common.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;

/**
 * @author khoi.tran on 1/25/17.
 */
public class BaseNeo4jEntity {
    @GraphId
    private Long id;

    @DateLong
    private Date createdDateTime;

    @DateLong
    private Date updatedDateTime;

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
