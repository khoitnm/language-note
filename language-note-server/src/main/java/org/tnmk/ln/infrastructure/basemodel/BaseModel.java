package org.tnmk.ln.infrastructure.basemodel;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * @author khoi.tran on 1/25/17.
 */
public class BaseModel {
    private Long id;

    private Date createdDateTime;

    private Date updatedDateTime;

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (this.getClass() != object.getClass()) return false;
        BaseModel baseNeo4jEntity = (BaseModel) object;
        if (this.id == null || baseNeo4jEntity.id == null) return false;
        return this.id.equals(baseNeo4jEntity.id);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public String toString() {
        return this.getClass().getSimpleName() + this.id;
    }

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
