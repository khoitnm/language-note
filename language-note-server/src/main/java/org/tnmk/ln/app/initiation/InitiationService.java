package org.tnmk.ln.app.initiation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * The initiation methods in this class will be run automatically when we deploy the project.
 * It's usually used for migration or data preparation.
 * @author khoi.tran on 2/26/17.
 */
@Service
public class InitiationService {
    public static Logger LOGGER = LoggerFactory.getLogger(InitiationService.class);

    @Autowired
    private AuthServerUserService authServerUserService;

    @Autowired
    private ExpressionService expressionService;

    @PostConstruct
    public void initAdminUser() {
        AuthServerUser user = authServerUserService.findByUsername(ResourceServerUserService.USERNAME_ADMIN);
        if (user == null) {
            user = constructDefaultUser();
            authServerUserService.registerUser(user);
        }
    }

    //TODO temporary code, move it out.
    public static AuthServerUser constructDefaultUser(){
        AuthServerUser user = new AuthServerUser();
        user.setUsername(ResourceServerUserService.USERNAME_ADMIN);
        user.setEmail("khoi.tnm@gmail.com");
        user.setPassword("superuser");
        user.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        return user;
    }
}
