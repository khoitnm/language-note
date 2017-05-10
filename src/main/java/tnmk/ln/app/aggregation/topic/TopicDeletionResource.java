package tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 4/1/17.
 */
@RestController
public class TopicDeletionResource {

    @Autowired
    private TopicDeletionService topicDeletionService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/{topicId}/expressions/{expressionId}", method = RequestMethod.DELETE)
    public void detachExpressionFromTopic(@PathVariable Long topicId, @PathVariable String expressionId) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        topicDeletionService.detachExpressionFromTopic(user, topicId, expressionId);
    }
}
