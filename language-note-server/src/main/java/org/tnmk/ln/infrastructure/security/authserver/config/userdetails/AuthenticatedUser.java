package org.tnmk.ln.infrastructure.security.authserver.config.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUser;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class AuthenticatedUser implements UserDetails {
    private static final long serialVersionUID = -382030618755656362L;
    private final AuthServerUser user;

    public AuthenticatedUser(AuthServerUser user) {
        this.user = user;
    }

    public AuthServerUser getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRoles() != null) {
            return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}