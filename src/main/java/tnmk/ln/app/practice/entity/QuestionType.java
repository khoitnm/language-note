package tnmk.ln.app.practice.entity;

/**
 * @author khoi.tran on 2/25/17.
 */
public enum QuestionType {
    EXPRESSION_RECALL(QuestionExpressionRecall.LOGIC_NAME), LISTENING(QuestionListening.LOGIC_NAME), FILL_BLANK(QuestionFillBlank.LOGIC_NAME), FILL_BLANK_ONE_CHOICE(QuestionFillBlankOneChoice.LOGIC_NAME), FILL_BLANK_MULTI_CHOICES(QuestionFillBlankMultiChoice.LOGIC_NAME);
    private final String logicName;

    QuestionType(String logicName) {this.logicName = logicName;}

    public String getLogicName() {
        return logicName;
    }
}
