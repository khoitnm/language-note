package tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.aggregation.TopicCompositeService;
import tnmk.ln.app.aggregation.TopicDeletionService;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @Autowired
    TopicCompositeService topicCompositeService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/construct", method = RequestMethod.GET)
    public Topic construct() {
        return TopicFactory.constructSchema();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/mine", method = RequestMethod.GET)
    public List<Topic> getTopicBriefsByOnwer() {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicService.getTopicBriefsByOwner(user);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/{topicId}/detail", method = RequestMethod.GET)
    public Topic getDetailTopic(@PathVariable Long topicId) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicService.findDetailById(topicId);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/name", method = RequestMethod.PUT)
    public void rename(@RequestBody Topic topic) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        topicService.rename(topic);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.POST)
    public Topic saveOnlyTopic(@RequestBody Topic topic) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicService.saveTopic(user, topic);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.DELETE)
    public void delete(@RequestBody RemoveRequest removeRequest) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        topicDeletionService.deleteTopicAndRelations(user, removeRequest.id, removeRequest.removeCompositions);
    }

    public static class RemoveRequest {
        private long id;
        private boolean removeCompositions;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isRemoveCompositions() {
            return removeCompositions;
        }

        public void setRemoveCompositions(boolean removeCompositions) {
            this.removeCompositions = removeCompositions;
        }
    }
}
