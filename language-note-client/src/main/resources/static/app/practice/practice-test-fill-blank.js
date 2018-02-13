//////////////////////////////////////////////////////////////////////////////////

var ExpressionsFillBlankTest = function (questionsWithPracticeResult, totalQuestions) {
    //Call super constructor
    QuestionsTest.call(this, questionsWithPracticeResult, totalQuestions);
};
inherit(QuestionsTest, ExpressionsFillBlankTest);
ExpressionsFillBlankTest.prototype.checkResult = function () {
    var self = this;
    self.answered = true;
    for (var i = 0; i < self.askedItems.length; i++) {
        var questionWithPracticeResult = self.askedItems[i];
        questionWithPracticeResult.answerResult = 1;
        var questionParts = questionWithPracticeResult.question.questionParts;
        for (var iquestionPart = 0; iquestionPart < questionParts.length; iquestionPart++) {
            var questionPart = questionParts[iquestionPart];
            if (questionPart.questionPartType == 'BLANK' && (isBlank(questionPart.answer) || questionPart.text.toLowerCase() != questionPart.answer.toLowerCase())) {
                questionWithPracticeResult.answerResult = -1;
                break;
            }
        }

    }
};

