package tnmk.ln.app.security.user;

import org.springframework.stereotype.Component;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.infrastructure.security.service.UserService;

/**
 * @author khoi.tran on 3/31/17.
 */
@Component
public class UserAuthorization {
    public static boolean isAdmin(User user) {
        return (user.getUsername().equals(UserService.USERNAME_ADMIN));
    }
}
