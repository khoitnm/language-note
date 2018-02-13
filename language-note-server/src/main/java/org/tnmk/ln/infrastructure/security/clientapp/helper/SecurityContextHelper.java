package org.tnmk.ln.infrastructure.security.clientapp.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tnmk.common.exception.AuthenticationException;
import org.tnmk.ln.infrastructure.security.model.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;

import javax.servlet.http.HttpServletRequest;

public final class SecurityContextHelper {
    private static Logger log = LoggerFactory.getLogger(SecurityContextHelper.class);

    private SecurityContextHelper() {
    }

    /**
     * @return If UserAccess is not null, UserAccess.user and UserAccess.consumer are always not null.
     */
    public static User getUser() {
        User result = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object userDetailsObject = auth.getPrincipal();
            if (userDetailsObject != null && userDetailsObject instanceof AuthenticatedUser) {
                AuthenticatedUser userAuthentication = (AuthenticatedUser) userDetailsObject;
                result = userAuthentication.getUser();
            }
        }
        return result;
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
    public static User validateExistAuthenticatedUser() {
        User user = getUser();
        if (null == user) {
            log.error("Not found AuthenticatedUser information in request. "
                    + "The request was not authorized."
                    + "Please check to register requestURL to AuthenticationInterceptor class.");
            throw new AuthenticationException("Unauthenticated!");
        }
        return user;
    }
}
