package org.tnmk.ln.app.aggregation.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.practice.entity.question.QuestionType;
import org.tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class QuestionRecommendationResource {

    @Autowired
    private QuestionRecommendationService questionRecommendationService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/questions/recommendation", method = RequestMethod.POST)
    public List<QuestionWithPracticeResult> findAllQuestionRecommendation(@RequestBody QuestionRecommendationRequest questionRecommendationRequest) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();

        return questionRecommendationService.loadQuestionsByTopics(user.getId(), questionRecommendationRequest.questionType, questionRecommendationRequest.topicIds);
    }

    public static class QuestionRecommendationRequest {
        private QuestionType questionType;
        private List<Long> topicIds;

        public QuestionType getQuestionType() {
            return questionType;
        }

        public void setQuestionType(QuestionType questionType) {
            this.questionType = questionType;
        }

        public List<Long> getTopicIds() {
            return topicIds;
        }

        public void setTopicIds(List<Long> topicIds) {
            this.topicIds = topicIds;
        }
    }
}
