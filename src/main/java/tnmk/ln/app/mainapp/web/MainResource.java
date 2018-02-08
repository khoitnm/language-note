package tnmk.ln.app.mainapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 11/6/16.
 */
@Controller
public class MainResource {
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

//    @RequestMapping("/")
//    public String defaultPage(Model model) {
//        return greetingPage(model);
//    }

    @RequestMapping({ "/", UriPrefixConstants.WEB_PREFIX + "/main"})
    public String greetingPage(Model model) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        model.addAttribute("projectInfo", projectInfoProperties);
        model.addAttribute("user", user);
        return "main";
    }
}
