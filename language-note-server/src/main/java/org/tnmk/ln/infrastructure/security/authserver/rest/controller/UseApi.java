package org.tnmk.ln.infrastructure.security.authserver.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.infrastructure.security.authserver.helper.AuthServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

@Controller
public class UseApi {
    public static final Logger LOGGER = LoggerFactory.getLogger(UseApi.class);
    @Autowired
    private AuthServerUserService authServerUserService;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    DefaultTokenServices tokenServices;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/users", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return authServerUserService.registerUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    @ResponseBody
    public AuthenticatedUser getUserProfile(Authentication auth) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)auth;
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        String accessToken = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        LOGGER.info("Additional"+oAuth2AccessToken.getAdditionalInformation());
//        tokenServices.

        AuthenticatedUser authenticatedUser = AuthServerSecurityContextHelper.getUser();
        return authenticatedUser;
    }
}
