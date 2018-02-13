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
