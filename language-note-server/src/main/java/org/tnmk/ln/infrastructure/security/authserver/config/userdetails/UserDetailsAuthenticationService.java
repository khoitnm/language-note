package org.tnmk.ln.infrastructure.security.authserver.config.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 1/28/17.
 */
@Service("userDetailsService")
public class UserDetailsAuthenticationService implements UserDetailsService {
    private final AuthServerUserService authServerUserService;

    @Autowired
    public UserDetailsAuthenticationService(AuthServerUserService authServerUserService) {
        this.authServerUserService = authServerUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authServerUserService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find username '" + username + "'");
        }
        return new AuthenticatedUser(user);
    }
}
