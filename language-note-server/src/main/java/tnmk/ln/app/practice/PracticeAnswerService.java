package tnmk.ln.app.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.infrastructure.guardian.Guardian;
import tnmk.common.util.ListUtil;
import tnmk.ln.app.aggregation.practice.PracticeAnswerResource;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.result.AnswerResult;
import tnmk.ln.app.practice.entity.result.BasePracticeResult;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/9/17.
 */
@Service
public class PracticeAnswerService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PracticeAnswerService.class);

    public static final int MAX_POINTS_STORING = 20;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionPracticeResultRepository questionPracticeResultRepository;

    @Autowired
    private QuestionPracticeResultQueryRepository questionPracticeResultQueryRepository;

    @Autowired
    private ExpressionPracticeResultRepository expressionPracticeResultRepository;

    @Autowired
    private ExpressionPracticeResultQueryRepository expressionPracticeResultQueryRepository;

    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    @Transactional
    public List<AnswerResult> answerResult(User user, List<PracticeAnswerResource.PracticeAnswerRequest> practiceAnswerRequests) {
        List<AnswerResult> results = new ArrayList<>();
        for (PracticeAnswerResource.PracticeAnswerRequest practiceAnswerRequest : practiceAnswerRequests) {
            AnswerResult answerResult = answerResult(user, practiceAnswerRequest.getQuestionId(), practiceAnswerRequest.getAnswerPoint());
            results.add(answerResult);
        }
        return results;
    }

    @Transactional
    public AnswerResult answerResult(User user, long questionId, float answerPoint) {
        Question question = questionRepository.findOne(questionId, 3);
        Guardian.validateNotNull(question, "Question " + questionId + " doesn't exist. Cannot answer result for it");

        QuestionPracticeResult questionPracticeResult = saveQuestionAnswer(user, question, answerPoint);
        String fromExpressionId = question.getFromExpressionId();
        Guardian.validateNotNull(fromExpressionId, "Expression inside question must be not null.");
        ExpressionPracticeResult expressionPracticeResult = saveExpressionAnswer(user, fromExpressionId, answerPoint);
        return new AnswerResult(expressionPracticeResult, questionPracticeResult);
    }

    private QuestionPracticeResult saveQuestionAnswer(User user, Question question, float answerPoint) {
        long questionId = question.getId();
        QuestionPracticeResult practiceResult = questionPracticeResultQueryRepository.findByOwnerIdAndQuestionId(user.getId(), questionId);
        if (practiceResult == null) {
            practiceResult = new QuestionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setQuestion(question);
        } else {
            ListUtil.addToListWithMaxSize(practiceResult.getAnswers(), answerPoint, MAX_POINTS_STORING);
        }
        practiceResult.setSumLatestAnswerPoint(PracticeAnswerHelper.totalAnswerPoints(practiceResult.getAnswers(), PracticeAnswerHelper.LATEST_POINTS));
        return questionPracticeResultRepository.save(practiceResult);
    }

    private ExpressionPracticeResult saveExpressionAnswer(User user, String expressionId, float answerPoint) {
//        long expressionId = expression.getId();
        ExpressionPracticeResult practiceResult = expressionPracticeResultQueryRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
//        LOGGER.debug("ExpressionID {}: Before:\n answerPoint {}, sumPoint {}, answers: {}", expressionId, answerPoint, practiceResult.getSumLatestAnswerPoint(), practiceResult.getAnswers());
        if (practiceResult == null) {
            practiceResult = new ExpressionPracticeResult();
            practiceResult.setOwner(user);
            practiceResult.setAnswers(Arrays.asList(answerPoint));
            practiceResult.setExpressionId(expressionId);
        } else {
            ListUtil.addToListWithMaxSize(practiceResult.getAnswers(), answerPoint, MAX_POINTS_STORING);
        }
        int favourite = practiceFavouriteService.findExpressionFavourite(user.getId(), expressionId);
        practiceResult.setAdditionalPoints(-favourite);
        PracticeAnswerHelper.calculateAnswerPoints(practiceResult);
        return expressionPracticeResultRepository.save(practiceResult);
    }

}
