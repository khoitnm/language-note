package org.tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.aggregation.topic.TopicDeletionService;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class ExpressionResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @Autowired
    ExpressionService expressionService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/detail/lookup", method = RequestMethod.GET)
    public Expression lookupExpression(
            @RequestParam(value = "lang", required = false, defaultValue = Locale.CODE_EN) String language
            , @RequestParam(value = "text") String text) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        return expressionService.findLookUpDetailByText(language, text);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/brief/lookup", method = RequestMethod.GET)
    public Expression checkExistingExpressionByText(
            @RequestParam(value = "lang", required = false, defaultValue = Locale.CODE_EN) String language
            , @RequestParam(value = "text") String text) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        return expressionService.findOneBriefByText(text);
    }

}
