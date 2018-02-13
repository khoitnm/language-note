var ExpressionsRecallTest = function (questionsWithPracticeResult, totalQuestions) {
    //Call super constructor
    QuestionsTest.call(this, questionsWithPracticeResult, totalQuestions);
};
inherit(QuestionsTest, ExpressionsRecallTest);
ExpressionsRecallTest.prototype.checkResult = function () {
    var self = this;
    self.answered = true;
    for (var i = 0; i < self.askedItems.length; i++) {
        var questionWithPracticeResult = self.askedItems[i];
        if (isNotBlank(questionWithPracticeResult.answer) && questionWithPracticeResult.question.fromExpression.text == questionWithPracticeResult.answer) {
            questionWithPracticeResult.answerResult = 1;
        } else {
            questionWithPracticeResult.answerResult = -1;
        }
    }
};