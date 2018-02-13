package tnmk.ln.infrastructure.security.clientapp.resource.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.constant.UriPrefixConstants;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.infrastructure.security.service.UserService;

@RestController
public class UserResource {
    @Autowired
    private UserService userService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/users", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}