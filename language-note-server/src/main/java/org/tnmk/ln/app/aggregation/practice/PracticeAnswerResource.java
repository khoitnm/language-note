package org.tnmk.ln.app.aggregation.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.practice.entity.result.AnswerResult;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.practice.PracticeAnswerService;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.usermanagement.ResourceServerUserService;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;
import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class PracticeAnswerResource {


    @Inject
    private SecurityContextHelper securityContextHelper;

    @Inject
    private ResourceServerUserService resourceServerUserService;

    @Autowired
    private PracticeAnswerService practiceAnswerService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/questions/answers", method = RequestMethod.POST)
    public List<AnswerResult> findAllQuestionRecommendation(@RequestBody List<PracticeAnswerRequest> practiceAnswerRequests) {
        AccessTokenUserDetails accessTokenUserDetails = securityContextHelper.validateExistAuthenticatedUser();
        User user = resourceServerUserService.findById(accessTokenUserDetails.getUserId());
        List<AnswerResult> result = practiceAnswerService.answerResult(user, practiceAnswerRequests);
        return result;
    }

    public static class PracticeAnswerRequest {
        private long questionId;
        private float answerPoint;

        public long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }

        public float getAnswerPoint() {
            return answerPoint;
        }

        public void setAnswerPoint(float answerPoint) {
            this.answerPoint = answerPoint;
        }
    }
}
