var ExpressionsTest = function (expressionItems, totalQuestions) {
    //Call super constructor
    QuestionsTest.call(this, expressionItems, totalQuestions);
};
inherit(QuestionsTest, ExpressionsTest);
ExpressionsTest.prototype.checkResult = function () {
    var self = this;
    self.answered = true;
    for (var i = 0; i < self.expressionItemsForAsks.length; i++) {
        var question = self.expressionItemsForAsks[i];
        if (isNotBlank(question.expressionAnswer) && question.expression == question.expressionAnswer) {
            question.answerResult = 1;
        } else {
            question.answerResult = -1;
        }
    }
};