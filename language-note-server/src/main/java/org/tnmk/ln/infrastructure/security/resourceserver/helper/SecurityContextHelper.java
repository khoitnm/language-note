package org.tnmk.ln.infrastructure.security.resourceserver.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.authserver.helper.AuthServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * This class could be used in Repository layer. And it should not inject any classes on the higher layers.
 */
@Component
public class SecurityContextHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextHelper.class);

    /**
     * For making it quick, we can reuse from AuthServer.
     */
    @Inject
    private AuthServerSecurityContextHelper authServerSecurityContextHelper;

    public AccessTokenUserDetails getUser() {
        return authServerSecurityContextHelper.getUser();
//
//
//        User result = null;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) auth;
//            Object userDetailsObject = oauth2Authentication.getDetails();
//            if (userDetailsObject != null && userDetailsObject instanceof AuthenticatedUser) {
//                AuthenticatedUser userAuthentication = (AuthenticatedUser) userDetailsObject;
//                result = userAuthentication.getUser();
//            }else{
//                LOGGER.warn("Cannot convert authentication object to User object {}", auth);
//            }
//        }
//        return result;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    /**
     * This method will get authenticated user from RequestContextHolder.<br/>
     * If there is no authenticated user, throw exception.
     *
     * @return Never return null.
     */
    public AccessTokenUserDetails validateExistAuthenticatedUser() {
        AccessTokenUserDetails authenticatedUser = getUser();
        if (null == authenticatedUser) {
            LOGGER.error("Not found AuthenticatedUser information in request. "
                    + "The request was not authorized."
                    + "Please check to register requestURL to AuthenticationInterceptor class.");
            throw new RuntimeException("Unauthenticated!");
        }
        return authenticatedUser;
    }

}
