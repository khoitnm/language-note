package org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;
import java.util.List;

/**
 * This class contains public information which will be stored in accessToken.
 * So it should contains only public information. There should be no sensitive information like email, roles, password...
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenUserDetails {
    private Long userId;
    private String username;
    @DateLong
    private Date createdDateTime;
    @DateLong
    private Date updatedDateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
