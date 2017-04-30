var PracticeService = function ($http, $q, $routeParams, $sce) {
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.$sce = $sce;

    //this.lessonFilter = new FilterCollection($sce, "title");
    this.topicFilter = new FilterCollection($sce, "title");
    this.totalQuestions = 10;
    this.questionType = 'FILL_BLANK';
    this.expressionTest = undefined; //new ExpressionsRecallTest();//just a dummy object
    ExpressionService.call(this);
};
inherit(ExpressionService, PracticeService);
PracticeService.prototype.init = function () {
    var self = this;
    var topicsGet = self.$http.get(contextPath + '/api/topic-briefs/mine');
    self.$q.all([topicsGet]).then(function (arrayOfResults) {
        var topics = arrayOfResults[0].data;

        self.topicFilter.initByOriginalItems(topics, self.$routeParams.topicId);

        if (self.topicFilter.selectedItems.length > 0) {
            self.filterQuestions();
        }
    });
};
PracticeService.prototype.filterQuestions = function () {
    var self = this;
    var topicIds = getArrayByFields(self.topicFilter.selectedItems, "id");
    var filter = {
        questionType: self.questionType
        , topicIds: topicIds
    };
    self.$http.post(contextPath + "/api/questions/recommendation", filter).then(function (successResponse) {
        self.questionsWithPracticeResult = successResponse.data;
        self.initTestQuestions();
    });
};
/**
 * @param test an instance of ExpressionTest or ExpressionMeaningTest
 */
PracticeService.prototype.submitAnswers = function (test) {
    var self = this;
    test.checkResult();
    var answerRequest = [];
    for (var i = 0; i < test.askedItems.length; i++) {
        var askedItem = test.askedItems[i];
        answerRequest.push({'questionId': askedItem.question.id, 'answerPoint': askedItem.answerResult});
    }
    self.$http.post(contextPath + "/api/questions/answers", answerRequest).then(function (successResponse) {
        var answerResults = successResponse.data;
        //TODO set to current test.
        for (var i = 0; i < self.questionsWithPracticeResult.length; i++) {
            var questionsWithPracticeResult = self.questionsWithPracticeResult[i];
            var answerResult = answerResults.findItemByField('questionPracticeResult.question.id', questionsWithPracticeResult.question.id);
            questionsWithPracticeResult.expressionPracticeResult = answerResult.expressionPracticeResult;
        }
        //$r.copyProperties(answerResults, self.questionsWithPracticeResult);
    });
};
PracticeService.prototype.initTestQuestions = function () {
    if (this.questionType == 'EXPRESSION_RECALL') {
        this.expressionTest = new ExpressionsRecallTest(this.questionsWithPracticeResult, this.totalQuestions);
    } else {
        this.expressionTest = new ExpressionsFillBlankTest(this.questionsWithPracticeResult, this.totalQuestions);
    }
};

//TODO this method is duplicated in file expression-item-edit-service.js
PracticeService.prototype.favourite = function (expressionItem) {
    var self = this;
    var userPoint = expressionItem.userPoints[USER_ID];
    if (!hasValue(userPoint)) {
        userPoint = {};
        expressionItem.userPoints[USER_ID] = userPoint;
    }
    userPoint.favourite = (userPoint.favourite == -1) ? 0 : -1;
    var favouriteUpdateRequest = {
        expressionId: expressionItem.id
        , favourite: userPoint.favourite
    };
    self.$http.post(contextPath + "/api/expression-items/favourite", favouriteUpdateRequest).then(function (successResponse) {
        var updatedRowsCount = successResponse.data;
        if (updatedRowsCount <= 0) {
            console.log("Something wrong, there's no expression favourite is updated: " + updatedRowsCount);
        }
    });
};
angularApp.service('practiceService', ['$http', '$q', '$routeParams', '$sce', PracticeService]);
angularApp.controller('practiceController', ['$scope', 'practiceService', function ($scope, practiceService) {
    $scope.service = practiceService;
    $scope.USER_ID = USER_ID;
    practiceService.init();
}]);