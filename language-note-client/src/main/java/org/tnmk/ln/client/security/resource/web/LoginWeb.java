package org.tnmk.ln.client.security.resource.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import org.tnmk.ln.client.common.constant.UriPrefixConstants;
import org.tnmk.ln.client.security.AuthenticationServerProxy;
import org.tnmk.ln.client.security.model.OAuth2AccessTokenResponse;
import org.tnmk.ln.client.security.model.User;

/**
 * @author khoi.tran on 11/6/16.
 */
@Controller
public class LoginWeb {
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

    @Autowired
    private AuthenticationServerProxy authenticationServerProxy;

    @RequestMapping(value = {UriPrefixConstants.WEB_PREFIX + "/login"}, method = RequestMethod.GET)
    public String openLoginPage(Model model) {
        model.addAttribute("projectInfo", projectInfoProperties);
        return "login";
    }


    @RequestMapping(value = {UriPrefixConstants.WEB_PREFIX + "/login"}, method = RequestMethod.POST)
    public String login(@ModelAttribute LoginRequest loginRequest, Model model) {
        OAuth2AccessTokenResponse oAuth2AccessTokenResponse = authenticationServerProxy.login(loginRequest.getUsername(), loginRequest.getPassword());
        User user = authenticationServerProxy.getMyProfile(oAuth2AccessTokenResponse.getTokenType(), oAuth2AccessTokenResponse.getAccessToken());
        model.addAttribute("projectInfo", projectInfoProperties);
        model.addAttribute("user", user);
        model.addAttribute("oAuth2AccessTokenResponse", oAuth2AccessTokenResponse);
        return "main";
    }

    @RequestMapping(UriPrefixConstants.WEB_PREFIX + "/register")
    public String registerUser() {
        return "register";
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
