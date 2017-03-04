package tnmk.ln.infrastructure.security.neo4j.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Index;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
public class User extends BaseNeo4jEntity {
    @NotBlank
    @Index(unique = true)
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
