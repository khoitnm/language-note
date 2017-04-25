// /////////////////////////////////////////////////////////////////////////////////////////////
var QuestionsTest = function (originalItems, questionsCount) {
    this.originalItems = originalItems;
    this.answered = false;
    this.askedItems = [];
    this.initTest(questionsCount);
};
QuestionsTest.prototype.initTest = function (questionsCount) {
    this.askedItems = this.originalItems.slice();
    this.askedItems = this.askedItems.copyTop(questionsCount);
    shuffleArray(this.askedItems);
};
// /////////////////////////////////////////////////////////////////////////////////////////////

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
            if (questionPart.questionPartType == 'BLANK' && (isBlank(questionPart.answer) || questionPart.text != questionPart.answer)) {
                questionWithPracticeResult.answerResult = -1;
                break;
            }
        }

    }
};