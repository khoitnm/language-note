package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.aggregation.TopicDeletionService;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Locale;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class ExpressionResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @Autowired
    ExpressionService expressionService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/detail/lookup", method = RequestMethod.GET)
    public Expression lookupExpression(
            @RequestParam(value = "lang", required = false, defaultValue = Locale.LANGUAGE_EN) String language
            , @RequestParam(value = "text") String text) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return expressionService.findLookUpDetailByText(language, text);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/brief/lookup", method = RequestMethod.GET)
    public Expression checkExistingExpressionByText(
            @RequestParam(value = "lang", required = false, defaultValue = Locale.LANGUAGE_EN) String language
            , @RequestParam(value = "text") String text) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return expressionService.findOneBriefByText(text);
    }
}
