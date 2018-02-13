package org.tnmk.ln.client.resource.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import org.tnmk.ln.client.common.constant.UriPrefixConstants;

/**
 * @author khoi.tran on 11/6/16.
 */
@Controller
public class LoginResource {
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

    @RequestMapping({UriPrefixConstants.WEB_PREFIX + "/login" })
    public String loginPage(Model model) {
        model.addAttribute("projectInfo", projectInfoProperties);
        return "login";
    }
}
