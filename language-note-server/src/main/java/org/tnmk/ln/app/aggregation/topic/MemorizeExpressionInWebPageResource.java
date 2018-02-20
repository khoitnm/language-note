package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.aggregation.topic.model.MemorizeExpressionInWebPageRequest;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.dictionary.ExpressionResource;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.app.topic.TopicResource;
import org.tnmk.ln.app.topic.TopicService;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class MemorizeExpressionInWebPageResource {
    @Autowired
    private TopicService topicService;

    @Inject
    private TopicCompositeResource topicCompositeResource;

    @Inject
    private ExpressionResource expressionResource;

    @Inject
    private TopicResource topicResource;

    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @Inject
    private ResourceServerUserService resourceServerUserService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression-in-page:memorize", method = RequestMethod.POST)
    public Expression memorizeExpressionInPage(@RequestBody MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest) {
        AccessTokenUserDetails accessTokenUserDetails = resourceServerSecurityContextHelper.getAccessTokenUserDetails();
        if (accessTokenUserDetails == null) {
            return memorizeExpressionForAnonymous(memorizeExpressionInWebPageRequest);
        } else {
            Long userId = accessTokenUserDetails.getUserId();
            return memorizeExpressionForAuthenticatedUser(userId, memorizeExpressionInWebPageRequest);
        }
    }

    private Expression lookupExpression(MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest){
        String locale = memorizeExpressionInWebPageRequest.getLocale();
        if (org.apache.commons.lang3.StringUtils.isBlank(locale)) {
            locale = Locale.CODE_EN;
        }
        return expressionResource.lookupExpression(locale, memorizeExpressionInWebPageRequest.getExpression());
    }

    private Expression memorizeExpressionForAnonymous(MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest){
        return lookupExpression(memorizeExpressionInWebPageRequest);
    }

    private Expression memorizeExpressionForAuthenticatedUser(Long userId, MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest){
        Expression expression = lookupExpression(memorizeExpressionInWebPageRequest);
        //Save expression
        User user = resourceServerUserService.findById(userId);
        expression.setOwner(user);
        expression = topicCompositeResource.saveExpressionComposite(expression);

        Topic topic = createTopicIfNotFound(userId, memorizeExpressionInWebPageRequest);
        addExpressionToTopicIfNecessary(expression, topic);
        return expression;
    }
    private Topic createTopicIfNotFound(Long userId, MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest){
        String topicTitle = memorizeExpressionInWebPageRequest.getPageTitle().trim();
        List<Topic> topics = topicService.lookupByTitleAndOwner(userId, topicTitle);
        Topic topic;
        if (topics.isEmpty()) {
            topic = createTopicFromMemorizeRequest(memorizeExpressionInWebPageRequest);
        } else {
            topic = topics.get(0);
        }
        return topic;
    }
    private Topic createTopicFromMemorizeRequest(MemorizeExpressionInWebPageRequest memorizeExpressionInWebPageRequest){
        Topic topic = new Topic();
        topic.setTitle(memorizeExpressionInWebPageRequest.getPageTitle());
        String pageUrl = memorizeExpressionInWebPageRequest.getPageUrl();
        String titleDescription = String.format("<a href='%s'>%s</a>", pageUrl, pageUrl);
        topic.setNoteAsHtml(titleDescription);
        topic = topicResource.saveOnlyTopic(topic);
        return topic;
    }

    private void addExpressionToTopicIfNecessary(Expression expression, Topic topic){
        if (topic.getExpressionIds() == null){
            topic.setExpressionIds(new ArrayList<>());
        }
        if (!topic.getExpressionIds().contains(expression.getId())) {
            topic.getExpressionIds().add(expression.getId());
            topicResource.saveOnlyTopic(topic);
        }
    }
}
