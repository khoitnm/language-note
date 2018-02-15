package org.tnmk.ln.infrastructure.security.resourceserver.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UseExtraController {

    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/user/extra")
    @ResponseBody
    public Map<String, Object> getUserProfile() {
        User resourceUser = resourceServerSecurityContextHelper.validateExistUser();
        Map<String, Object> details = new HashMap<>();
        details.put("getDetails", resourceUser);
        return details;
    }
}
