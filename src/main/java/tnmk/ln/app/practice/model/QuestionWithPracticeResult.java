package tnmk.ln.app.practice.model;

import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.result.QuestionPracticeResult;

import javax.annotation.Nullable;

/**
 * @author khoi.tran on 3/30/17.
 */
public class QuestionWithPracticeResult {
    private Question question;
    /**
     * This value can be null because there's no answer for the question yet, but there may be already other answers of other questions related to the same expression.
     */
    @Nullable
    private QuestionPracticeResult questionPracticeResult;
    private ExpressionPracticeResult expressionPracticeResult;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
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
