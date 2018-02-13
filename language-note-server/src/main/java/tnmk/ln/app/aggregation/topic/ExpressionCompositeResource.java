package tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.common.constant.UriPrefixConstants;
import tnmk.ln.app.dictionary.ExpressionFactory;
import tnmk.ln.app.dictionary.entity.Locale;
import tnmk.ln.infrastructure.security.clientapp.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

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
