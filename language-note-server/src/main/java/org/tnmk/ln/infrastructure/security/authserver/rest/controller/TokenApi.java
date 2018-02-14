package org.tnmk.ln.infrastructure.security.authserver.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TokenApi {
    public static final Logger LOGGER = LoggerFactory.getLogger(TokenApi.class);
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("tokenServices")
    private DefaultTokenServices consumerTokenServices;

    /**
     * TODO
     * <p>
     * <strong>NOTE:</strong> Below code run correctly, the accessToken will be deleted from tokenStore and consumerTokenServices.
     * However, after revoking the token, other request still be able to use that token. The reason is we are using JWT token.
     * </p>
     *
     * <p>
     * <strong>Note2:</strong>
     * This method only covers the standard token implementation in the framework, not JWT tokens.
     * Such a logout is not possible with JWT tokens.
     * JWT token is self-contained, which means that all information regarding the authentication are in the token itself. If you want to check, if a user is logged in, you just need to check the signature in the JWT token and the token expiration time. No communication with a server is required.
     * </p>
     *
     * @param auth
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/revoke-token")
    @ResponseBody
    public void logout(Authentication auth, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        String tokenValue = oAuth2AuthenticationDetails.getTokenValue();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        //This is on ResourceServer???
        consumerTokenServices.revokeToken(tokenValue);
        tokenStore.removeAccessToken(accessToken);
    }

    private String getReferrerUrl(HttpServletRequest request) {
        return request.getHeader("referrer");
    }


}
