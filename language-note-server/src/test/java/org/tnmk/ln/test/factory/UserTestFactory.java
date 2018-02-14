package org.tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.Contributor;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class UserTestFactory {
    @Autowired
    private AuthServerUserService authServerUserService;

    public User initDefaultUser() {
        User user = authServerUserService.findByUsername(ResourceServerUserService.USERNAME_ADMIN);
        if (user == null) {
            user = new Contributor();
            authServerUserService.registerUser(user);
        }
        return user;
    }

    public static User constructUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}
