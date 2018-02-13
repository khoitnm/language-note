package org.tnmk.ln.app.practice.model;

import org.tnmk.ln.app.aggregation.practice.model.QuestionComposite;
import org.tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import org.tnmk.ln.app.practice.entity.result.QuestionPracticeResult;

import javax.annotation.Nullable;

/**
 * @author khoi.tran on 3/30/17.
 */
public class QuestionWithPracticeResult {
    private QuestionComposite question;
    /**
     * This value can be null because there's no answer for the question yet, but there may be already other answers of other questions related to the same expression.
     */
    @Nullable
    private QuestionPracticeResult questionPracticeResult;
    private ExpressionPracticeResult expressionPracticeResult;

    public QuestionComposite getQuestion() {
        return question;
    }

    public void setQuestion(QuestionComposite question) {
        this.question = question;
    }

    public QuestionPracticeResult getQuestionPracticeResult() {
        return questionPracticeResult;
    }

    public void setQuestionPracticeResult(QuestionPracticeResult questionPracticeResult) {
        this.questionPracticeResult = questionPracticeResult;
    }

    public ExpressionPracticeResult getExpressionPracticeResult() {
        return expressionPracticeResult;
    }

    public void setExpressionPracticeResult(ExpressionPracticeResult expressionPracticeResult) {
        this.expressionPracticeResult = expressionPracticeResult;
    }
}
