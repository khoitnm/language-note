package org.tnmk.ln.infrastructure.security.authserver.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.authserver.helper.AuthServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;

@RestController
public class UseApi {
    public static final Logger LOGGER = LoggerFactory.getLogger(UseApi.class);
    @Autowired
    private AuthServerUserService authServerUserService;

    @Autowired
    private AuthServerSecurityContextHelper authServerSecurityContextHelper;

    //TODO must check whether the password is transferred to requestBody.
    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/users", method = RequestMethod.POST)
    public AuthServerUser registerUser(@RequestBody AuthServerUser user) {
        return authServerUserService.registerUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public AuthServerUser getUserProfile() {
        AccessTokenUserDetails accessTokenUserDetails = authServerSecurityContextHelper.getUser();
        return authServerUserService.findById(accessTokenUserDetails.getUserId());
    }
}
