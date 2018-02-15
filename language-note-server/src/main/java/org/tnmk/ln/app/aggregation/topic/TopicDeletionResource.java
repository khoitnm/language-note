package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.UserConverter;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * @author khoi.tran on 4/1/17.
 */
@RestController
public class TopicDeletionResource {

    @Inject
    private SecurityContextHelper securityContextHelper;

    @Inject
    private ResourceServerUserService resourceServerUserService;

    @Autowired
    private TopicDeletionService topicDeletionService;


    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/{topicId}/expressions/{expressionId}", method = RequestMethod.DELETE)
    public void detachExpressionFromTopic(@PathVariable Long topicId, @PathVariable String expressionId) {
        AccessTokenUserDetails accessTokenUserDetails = securityContextHelper.validateExistAuthenticatedUser();
        User user = resourceServerUserService.findById(accessTokenUserDetails.getUserId());
        topicDeletionService.detachExpressionFromTopic(user, topicId, expressionId);
    }
}
