package org.tnmk.ln.infrastructure.security.authserver.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.tnmk.ln.infrastructure.security.authserver.rest.dto.model.AuthenticatedUser;

public final class AuthServerSecurityContextHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthServerSecurityContextHelper.class);

    private AuthServerSecurityContextHelper() {
    }

    /**
     * @return If UserAccess is not null, UserAccess.user and UserAccess.consumer are always not null.
     */
    public static AuthenticatedUser getUser() {
        AuthenticatedUser result = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) auth;
            Object userDetailsObject = oauth2Authentication.getDetails();
            if (userDetailsObject != null && userDetailsObject instanceof AuthenticatedUser) {
                result = (AuthenticatedUser) userDetailsObject;
            }else{
                LOGGER.warn("Cannot convert authentication object to User object {}", auth);
            }
        }
        return result;
    }

    /**
     * This method will get authenticated user from RequestContextHolder.<br/>
     * If there is no authenticated user, throw exception.
     *
     * @return Never return null.
     */
    public static AuthenticatedUser validateExistAuthenticatedUser() {
        AuthenticatedUser authenticatedUser = getUser();
        if (null == authenticatedUser) {
            LOGGER.error("Not found AuthenticatedUser information in request. "
                    + "The request was not authorized."
                    + "Please check to register requestURL to AuthenticationInterceptor class.");
            throw new RuntimeException("Unauthenticated!");
        }
        return authenticatedUser;
    }

}
