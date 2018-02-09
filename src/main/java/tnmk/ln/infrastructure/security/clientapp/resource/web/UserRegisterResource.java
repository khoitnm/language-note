package tnmk.ln.infrastructure.security.clientapp.resource.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tnmk.ln.app.common.entity.UriPrefixConstants;

/**
 * @author khoi.tran on 1/28/17.
 */
@Controller
public class UserRegisterResource {
    @RequestMapping(UriPrefixConstants.WEB_PREFIX + "/register")
    public String registerUser() {
        return "register";
    }
}
