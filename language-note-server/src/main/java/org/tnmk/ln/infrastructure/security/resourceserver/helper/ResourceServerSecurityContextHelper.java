package org.tnmk.ln.infrastructure.security.resourceserver.helper;

import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * Never, ever use this class for repository layer.
 * You should use it only on Api & Web Controller layer.
 */
@Component
public class ResourceServerSecurityContextHelper {
    @Inject
    private SecurityContextHelper securityContextHelper;

    @Inject
    private ResourceServerUserService resourceServerUserService;
    public AccessTokenUserDetails getAccessTokenUserDetails() {
        return securityContextHelper.getUser();
    }
    public User validateExistUser(){
        AccessTokenUserDetails accessTokenUserDetails = securityContextHelper.validateExistAuthenticatedUser();
        User user = resourceServerUserService.findById(accessTokenUserDetails.getUserId());
        return user;
    }
}
