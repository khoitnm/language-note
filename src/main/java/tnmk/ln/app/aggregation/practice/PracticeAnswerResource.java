package tnmk.ln.app.aggregation.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.practice.PracticeAnswerService;
import tnmk.ln.app.practice.entity.result.AnswerResult;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class PracticeAnswerResource {

    @Autowired
    private PracticeAnswerService practiceAnswerService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/questions/answers", method = RequestMethod.POST)
    public List<AnswerResult> findAllQuestionRecommendation(@RequestBody List<PracticeAnswerRequest> practiceAnswerRequests) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return practiceAnswerService.answerResult(user, practiceAnswerRequests);
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
