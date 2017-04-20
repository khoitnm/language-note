// /////////////////////////////////////////////////////////////////////////////////////////////
var QuestionsTest = function (expressionItems, questionsCount) {
    this.expressionItems = expressionItems;
    this.answered = false;
    this.expressionItemsForAsks = [];
    this.initTest(questionsCount);
};
QuestionsTest.prototype.initTest = function (questionsCount) {
    this.expressionItemsForAsks = this.expressionItems.slice();
    this.expressionItemsForAsks = this.expressionItemsForAsks.copyTop(questionsCount);
    shuffleArray(this.expressionItemsForAsks);
};
// /////////////////////////////////////////////////////////////////////////////////////////////

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