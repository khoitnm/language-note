package org.tnmk.ln.app.topic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.app.dictionary.ExpressionRepository;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class TopicBriefService {
    public static int WHERE_TOPIC = 1;
    public static int WHERE_EXPRESSION = 2;
    public static int WHERE_TOPIC_AND_EXPRESSION = 3;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private TopicDetailRepository topicDetailRepository;

    //TODO add language
    public List<Topic> getTopicBriefsByOwner(User user, Integer where, String keyword) {
        List<Topic> topics;
        if (where == null) where = WHERE_TOPIC_AND_EXPRESSION;//default value

        if (StringUtils.isBlank(keyword)) {
            return getAllTopicBriefsByOwner(user);
        } else {
            String normKeyword = keyword.trim().toLowerCase();
            if (where == WHERE_TOPIC && StringUtils.isBlank(normKeyword)) {
                topics = getTopicBriefsByOwnerAndTitle(user, normKeyword);
            } else if (where == WHERE_EXPRESSION && StringUtils.isBlank(normKeyword)) {
                topics = getTopicBriefsByOwnerAndExpression(user, normKeyword);
            } else {
                topics = getTopicBriefsByOwnerAndTitleOrExpression(user, normKeyword);
            }
        }
        return topics;
    }

    private List<Topic> getTopicBriefsByOwnerAndExpression(User user, String expression) {
        String regExpression = ".*" + expression + ".*";
        List<Expression> expressionWithIds = expressionRepository.findIdsByContainText(regExpression);
        List<String> expressionIds = expressionWithIds.stream().map(iexpression -> iexpression.getId()).collect(Collectors.toList());
        return topicDetailRepository.findByOwnerAndExpressionIds(user.getId(), expressionIds);
    }

    private List<Topic> getTopicBriefsByOwnerAndTitleOrExpression(User user, String keyword) {
        String regExpression = ".*" + keyword + ".*";
        List<Expression> expressionWithIds = expressionRepository.findIdsByContainText(regExpression);
        List<String> expressionIds = expressionWithIds.stream().map(iexpression -> iexpression.getId()).collect(Collectors.toList());
        return topicDetailRepository.findByOwnerAndContainTitleOrExpressionIds(user.getId(), keyword, expressionIds);
    }

    public List<Topic> getAllTopicBriefsByOwner(User user) {
        return topicDetailRepository.findByOwner(user.getId());
    }

    public List<Topic> getTopicBriefsByOwnerAndTitle(User user, String title) {
        return topicDetailRepository.findByOwnerAndTitle(user.getId(), title);
    }
}
