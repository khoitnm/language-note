package org.tnmk.ln.app.vocabulary.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.tnmk.ln.app.vocabulary.util.UserPointUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 1/31/17.
 */
public class UserPoint {
    public static final int CALCULATE_POINTS_IN_LASTEST_TIMES = 2;
    private String userId;
    @Indexed
    private int favourite = 0;
    private List<Integer> answers;
    private int answersLength = 0;
    private Instant answerDateTime;
    //Below values are calculated from answers, they are only useful for querying.
    private AnswerCalculation latestAnswers;

    public UserPoint() {
        this.calculateCorrectAnswers();
    }

    public UserPoint(String userId, List<Integer> answers) {
        this.userId = userId;
        this.answers = answers;
        this.calculateCorrectAnswers();
    }

    private void calculateCorrectAnswers() {
        this.latestAnswers = UserPointUtils.calculateCorrectAnswersForLastElements(this.answers, CALCULATE_POINTS_IN_LASTEST_TIMES);
    }

    public List<Integer> getAnswers() {
        if (this.answers == null) {
            this.answers = new ArrayList<>();
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.addAll(this.answers);
        return result;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = new ArrayList<>();
        this.answers.addAll(answers);
        this.answersLength = answers.size();
        calculateCorrectAnswers();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AnswerCalculation getLatestAnswers() {
        return latestAnswers;
    }

    public void setLatestAnswers(AnswerCalculation latestAnswers) {
        this.latestAnswers = latestAnswers;
    }

    public int getAnswersLength() {
        return answersLength;
    }

    public void setAnswersLength(int answersLength) {
        this.answersLength = answersLength;
    }

    public Instant getAnswerDateTime() {
        return answerDateTime;
    }

    public void setAnswerDateTime(Instant answerDateTime) {
        this.answerDateTime = answerDateTime;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
