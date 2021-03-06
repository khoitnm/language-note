package org.tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.aggregation.topic.TopicCompositeService;
import org.tnmk.ln.app.aggregation.topic.TopicDeletionService;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicBriefService topicBriefService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @Autowired
    TopicCompositeService topicCompositeService;


    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/construct", method = RequestMethod.GET)
    public TopicComposite construct() {
        return TopicCompositeFactory.constructSchema();
    }

    /**
     * @param keyword
     * @param where
     * @return
     */
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/mine", method = RequestMethod.GET)
//    public List<Topic> getTopicBriefsByOwner(
//            @RequestParam(value = "keyword", required = false) String keyword,
//            @RequestParam(value = "where", required = false, defaultValue = "1") String where) {
//        User user = AuthServerSecurityContextHelper.validateExistAuthenticatedUser();
//        return topicBriefService.getTopicBriefsByOwner(user);
//    }
    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/{topicId}/detail", method = RequestMethod.GET)
    public TopicComposite getDetailTopic(@PathVariable String topicId) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        return topicCompositeService.findDetailById(user, topicId);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/name", method = RequestMethod.PUT)
    public void rename(@RequestBody Topic topic) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        topicService.rename(topic);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.POST)
    public Topic saveOnlyTopic(@RequestBody Topic topic) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        return topicService.saveTopic(user, topic);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.DELETE)
    public void delete(@RequestBody RemoveRequest removeRequest) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
        topicDeletionService.deleteTopicAndRelations(user, removeRequest.id, removeRequest.removeCompositions);
    }

    public static class RemoveRequest {
        private String id;
        private boolean removeCompositions;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
