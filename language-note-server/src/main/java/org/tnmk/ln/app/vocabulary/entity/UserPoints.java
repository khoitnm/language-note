package org.tnmk.ln.app.vocabulary.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author khoi.tran on 1/31/17.
 */
public class UserPoints extends HashMap<String, UserPoint> {
    public void putUserPoint(String userId, List<Integer> answers) {
        UserPoint userPoint = new UserPoint(userId, answers);
        userPoint.setAnswerDateTime(Instant.now());
        putUserPoint(userId, userPoint);
    }

    public void addAnswer(String userId, Integer newAnswer) {
        UserPoint userPoint = getUserPoint(userId);
        if (userPoint == null) {
            userPoint = new UserPoint(userId, new ArrayList<>());
            this.putUserPoint(userId, userPoint);
        }
        List<Integer> answers = userPoint.getAnswers();
        answers.add(newAnswer);
        userPoint.setAnswers(answers);
        userPoint.setAnswerDateTime(Instant.now());
    }

    private void putUserPoint(String userId, UserPoint userPoint) {
        userPoint.setUserId(userId);
        super.put(userId, userPoint);
    }

    public UserPoint getUserPoint(String userId) {
        return super.get(userId);
    }
}
