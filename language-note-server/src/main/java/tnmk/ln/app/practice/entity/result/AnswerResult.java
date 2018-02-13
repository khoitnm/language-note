package tnmk.ln.app.practice.entity.result;

/**
 * @author khoi.tran on 3/30/17.
 */
public class AnswerResult {

    private ExpressionPracticeResult expressionPracticeResult;
    private QuestionPracticeResult questionPracticeResult;

    public AnswerResult() {
    }

    public AnswerResult(ExpressionPracticeResult expressionPracticeResult, QuestionPracticeResult questionPracticeResult) {
        this.expressionPracticeResult = expressionPracticeResult;
        this.questionPracticeResult = questionPracticeResult;
    }

    public ExpressionPracticeResult getExpressionPracticeResult() {
        return expressionPracticeResult;
    }

    public void setExpressionPracticeResult(ExpressionPracticeResult expressionPracticeResult) {
        this.expressionPracticeResult = expressionPracticeResult;
    }

    public QuestionPracticeResult getQuestionPracticeResult() {
        return questionPracticeResult;
    }

    public void setQuestionPracticeResult(QuestionPracticeResult questionPracticeResult) {
        this.questionPracticeResult = questionPracticeResult;
    }
}
