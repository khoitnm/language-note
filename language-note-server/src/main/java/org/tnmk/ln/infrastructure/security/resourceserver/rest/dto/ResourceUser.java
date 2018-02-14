package org.tnmk.ln.infrastructure.security.resourceserver.rest.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Same as {@link org.tnmk.authserver.rest.dto.model.AuthenticatedUser}, but this model is used on ResourceServer
 */
public class ResourceUser {
    private Long id;

    @NotBlank
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
