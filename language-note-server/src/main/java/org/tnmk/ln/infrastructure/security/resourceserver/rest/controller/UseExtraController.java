package org.tnmk.ln.infrastructure.security.resourceserver.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UseExtraController {

    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/user/extra")
    @ResponseBody
    public Map<String, Object> getUserProfile(Authentication auth) {
        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) auth;
        User resourceUser = SecurityContextHelper.getUser();
        Map<String, Object> details = new HashMap<>();
        details.put("getDetails", oauth2Authentication.getDetails());
        details.put("getPrincipal", oauth2Authentication.getPrincipal());
        details.put("getCredentials", oauth2Authentication.getCredentials());
        details.put("getAuthorities", oauth2Authentication.getAuthorities());
        return details;
    }
}
