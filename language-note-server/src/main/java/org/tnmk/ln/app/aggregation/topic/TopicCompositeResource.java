package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.aggregation.topic.model.TopicComposite;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.topic.TopicBriefService;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicCompositeResource {
    @Autowired
    TopicService topicService;

    @Autowired
    TopicBriefService topicBriefService;
    @Autowired
    TopicDeletionService topicDeletionService;

    @Autowired
    TopicCompositeService topicCompositeService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topic-briefs/mine", method = RequestMethod.GET)
    public List<Topic> findAllTopicBriefs(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "where", required = false, defaultValue = "1") Integer where) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return topicBriefService.getTopicBriefsByOwner(user, where, keyword);
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
