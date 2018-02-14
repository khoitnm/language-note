package org.tnmk.ln.infrastructure.security.authserver.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.infrastructure.security.authserver.helper.AuthServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.authserver.rest.dto.model.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.infrastructure.security.usersmanagement.UserService;

@Controller
public class UseApi {
    @Autowired
    private UserService userService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/users", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    @ResponseBody
    public AuthenticatedUser getUserProfile(Authentication auth) {
        AuthenticatedUser authenticatedUser = AuthServerSecurityContextHelper.getUser();
        return authenticatedUser;
    }
}
