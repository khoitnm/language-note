package org.tnmk.ln.infrastructure.security.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.security.authserver.rest.dto.model.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.infrastructure.security.usersmanagement.UserService;

/**
 * @author khoi.tran on 1/28/17.
 */
@Service
public class UserDetailsAuthenticationService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find username '" + username + "'");
        }
        return new AuthenticatedUser(user);
    }
}
