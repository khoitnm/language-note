package tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.aggregation.TopicDeletionService;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/construct", method = RequestMethod.GET)
    public Topic construct() {
        return TopicFactory.constructSchema();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.DELETE)
    public void delete(@RequestBody RemoveRequest removeRequest) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        topicDeletionService.deleteTopicAndRelatedEntities(user, removeRequest.id, removeRequest.removeExpressions);
    }

    public static class RemoveRequest {
        private long id;
        private boolean removeExpressions;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isRemoveExpressions() {
            return removeExpressions;
        }

        public void setRemoveExpressions(boolean removeExpressions) {
            this.removeExpressions = removeExpressions;
        }
    }
}
