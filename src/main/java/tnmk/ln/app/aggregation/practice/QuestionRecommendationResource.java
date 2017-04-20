package tnmk.ln.app.aggregation.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

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
