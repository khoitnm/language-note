package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.dictionary.ExpressionFactory;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class ExpressionCompositeResource {
    @Autowired
    ExpressionCompositeService expressionCompositeService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-composites/construct", method = RequestMethod.GET)
    public ExpressionComposite construct() {
        return ExpressionFactory.constructSchema();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-composites/detail", method = RequestMethod.GET)
    public List<ExpressionComposite> searchExpressionByText(@RequestParam("q") String keyword, @RequestParam(value = "locale", defaultValue = Locale.DEFAULT_STRING) String locale) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return expressionCompositeService.findByKeyword(user, locale, keyword);
    }
}
