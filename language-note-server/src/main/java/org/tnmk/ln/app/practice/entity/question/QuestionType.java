package org.tnmk.ln.app.practice.entity.question;

/**
 * @author khoi.tran on 2/25/17.
 */
public enum QuestionType {
    EXPRESSION_RECALL(QuestionExpressionRecall.class, QuestionExpressionRecall.LOGIC_NAME),
    LISTENING(QuestionListening.class, QuestionListening.LOGIC_NAME),
    FILL_BLANK(QuestionFillBlank.class, QuestionFillBlank.LOGIC_NAME),
    FILL_BLANK_ONE_CHOICE(QuestionFillBlankOneChoice.class, QuestionFillBlankOneChoice.LOGIC_NAME),
    FILL_BLANK_MULTI_CHOICES(QuestionFillBlankMultiChoice.class, QuestionFillBlankMultiChoice.LOGIC_NAME);

    private final Class questionClass;
    private final String logicName;

    QuestionType(Class questionClass, String logicName) {
        this.questionClass = questionClass;
        this.logicName = logicName;
    }

    public String getLogicName() {
        return logicName;
    }

    public Class getQuestionClass() {
        return questionClass;
    }
}
