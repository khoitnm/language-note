package org.tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.Contributor;
import org.tnmk.ln.infrastructure.security.usersmanagement.UserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class UserTestFactory {
    @Autowired
    private UserService userService;

    public User initDefaultUser() {
        User user = userService.findByUsername(UserService.USERNAME_ADMIN);
        if (user == null) {
            user = new Contributor();
            userService.registerUser(user);
        }
        return user;
    }

    public static User constructUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}
