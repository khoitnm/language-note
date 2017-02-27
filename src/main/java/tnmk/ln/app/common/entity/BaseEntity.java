package tnmk.ln.app.common.entity;

import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * @author khoi.tran on 1/25/17.
 */
public class BaseEntity {
    @Id
    private String id;

    private Instant createdDateTime;
    private Instant updatedDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Instant getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Instant updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

}
