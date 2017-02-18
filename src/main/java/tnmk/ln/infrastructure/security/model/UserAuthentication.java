package tnmk.ln.infrastructure.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tnmk.ln.infrastructure.security.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserAuthentication implements UserDetails {
    private static final long serialVersionUID = -382030618755656362L;
    private final User user;

    public UserAuthentication(User user) {
        this.user = user;
    }

    public User getUser() {
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