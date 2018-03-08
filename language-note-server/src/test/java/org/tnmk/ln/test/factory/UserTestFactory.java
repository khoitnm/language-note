package org.tnmk.ln.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.UserConverter;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.Contributor;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 3/5/17.
 */
@Component
public class UserTestFactory {
    @Autowired
    private ResourceServerUserService resourceServerUserService;

    @Autowired
    UserConverter userConverter;
    @Autowired
    private AuthServerUserService authServerUserService;

    /**
     * @return
     */
    public User getDefaultUser() {
        String username = ResourceServerUserService.USERNAME_ADMIN;
        User user = resourceServerUserService.findByUsername(username);
        if (user == null) {
            throw new UnexpectedException("Not found default user! "+username);
        }
        return user;
    }

    public static User constructUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}
