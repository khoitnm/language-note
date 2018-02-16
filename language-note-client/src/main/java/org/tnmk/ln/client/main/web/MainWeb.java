package org.tnmk.ln.client.main.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;
import org.tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import org.tnmk.common.util.http.RequestUtils;
import org.tnmk.ln.client.common.constant.UriPrefixConstants;
import org.tnmk.ln.client.security.AuthenticationServerProxy;
import org.tnmk.ln.client.security.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainWeb {
    @Autowired
    private AuthenticationServerProxy authenticationServerProxy;

    @Autowired
    private ProjectInfoProperties projectInfoProperties;

    //FIXME there's no cookie at the beginning!
    @RequestMapping(UriPrefixConstants.WEB_PREFIX + "/main")
    public String main(Model model, HttpServletRequest request) {
        String accessToken = RequestUtils.getCookieValue(request, "access_token");
        String tokenType = RequestUtils.getCookieValue(request, "token_type");

        if (StringUtils.isBlank(accessToken)){
            return "redirect:"+UriPrefixConstants.WEB_PREFIX +"/login";
        }else{
            User user = authenticationServerProxy.getMyProfile(tokenType, accessToken);
            model.addAttribute("projectInfo", projectInfoProperties);
            model.addAttribute("user", user);
            return "main";
        }
    }
}
