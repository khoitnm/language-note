package tnmk.ln.app.practice.model;

import tnmk.ln.app.practice.entity.answer.ExpressionPracticeResult;
import tnmk.ln.app.practice.entity.answer.QuestionPracticeResult;
import tnmk.ln.app.practice.entity.question.Question;

/**
 * @author khoi.tran on 3/30/17.
 */
public class QuestionWithPracticeResult {
    private Question question;
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
