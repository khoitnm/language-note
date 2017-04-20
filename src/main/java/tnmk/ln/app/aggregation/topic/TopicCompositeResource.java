package tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.aggregation.topic.model.TopicComposite;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.topic.TopicService;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicCompositeResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicDeletionService topicDeletionService;

    @Autowired
    TopicCompositeService topicCompositeService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topic-briefs/mine", method = RequestMethod.GET)
    public List<Topic> findAllTopicBriefs() {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicService.getTopicBriefsByOwner(user);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topic-composites", method = RequestMethod.POST)
    public Topic saveTopicComposite(@RequestBody TopicComposite topic) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicCompositeService.saveTopicAndRelations(user, topic);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-composites", method = RequestMethod.POST)
    public Expression saveExpressionComposite(@RequestBody Expression expression) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicCompositeService.saveExpressionAndRelations(user, expression);
    }
}
