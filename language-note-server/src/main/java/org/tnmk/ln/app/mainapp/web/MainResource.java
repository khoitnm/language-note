package org.tnmk.ln.app.mainapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * @author khoi.tran on 11/6/16.
 */
@Controller
public class MainResource {
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @RequestMapping({ "/", UriPrefixConstants.WEB_PREFIX + "/main"})
    public String greetingPage(Model model) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        model.addAttribute("projectInfo", projectInfoProperties);
        model.addAttribute("user", user);
        return "main";
    }
}
