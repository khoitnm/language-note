package tnmk.ln.app.initiation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.infrastructure.security.entity.User;
import tnmk.ln.infrastructure.security.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author khoi.tran on 2/26/17.
 */
@Service
public class InitiationService {
    public static final String ADMIN_USERNAME = "admin";
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initAdminUser() {
        User user = userService.findByUsername("admin");
        if (user == null) {
            //TODO I know it's absolutely not secured.
            user = new User();
            user.setUsername(ADMIN_USERNAME);
            user.setEmail("khoi.tnm@gmail.com");
            user.setPassword("password");
            user.setRoles(Arrays.asList("ADMIN"));
            userService.registerUser(user);
        }
    }
}
