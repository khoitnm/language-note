package org.tnmk.ln.app.security.user;

import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 */
@Component
public class UserAuthorization {
    public static boolean isAdmin(User user) {
        return (user.getUsername().equals(ResourceServerUserService.USERNAME_ADMIN));
    }
}
