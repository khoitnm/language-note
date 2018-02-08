package tnmk.ln.app.mainapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tnmk.common.infrastructure.projectinfo.ProjectInfoProperties;
import tnmk.ln.app.common.entity.UriPrefixConstants;

/**
 * @author khoi.tran on 11/6/16.
 */
@Controller
public class LoginResource {
    @Autowired
    private ProjectInfoProperties projectInfoProperties;

//    @RequestMapping("/")
//    public String defaultPage(Model model) {
//        return loginPage(model);
//    }

    @RequestMapping({UriPrefixConstants.WEB_PREFIX + "/login" })
    public String loginPage(Model model) {
        model.addAttribute("projectInfo", projectInfoProperties);
        return "login";
    }
}
