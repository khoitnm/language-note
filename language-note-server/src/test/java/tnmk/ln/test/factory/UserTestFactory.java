package tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnmk.ln.infrastructure.security.neo4j.entity.Contributor;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.infrastructure.security.service.UserService;

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
