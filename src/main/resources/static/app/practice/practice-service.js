var PracticeService = function ($http, $q, $routeParams, $sce) {
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.$sce = $sce;

    //this.lessonFilter = new FilterCollection($sce, "title");
    this.topicFilter = new FilterCollection($sce, "title");
    this.expressions = [];
    this.totalQuestions = 10;
    this.expressionsRecallTest = new ExpressionsTest(this.expressions, this.totalQuestions);
};
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
    var filter = {
        questionType: 'EXPRESSION_RECALL'
        , topicIds: getArrayByFields(self.topicFilter.selectedItems, "id")
    };
    self.$http.post(contextPath + "/api/questions/recommendation", filter).then(function (successResponse) {
        self.questionsWithPracticeResult = successResponse.data;
        self.initTestQuestions();
    });
};

PracticeService.prototype.getLatestFiveAnswers = function (expressionItem) {
    var result;
    var userPoint = expressionItem.userPoints[USER_ID];
    if (hasValue(userPoint)) {
        result = userPoint.answers;
    } else {
        result = [];
    }
    var beginIndex = Math.max(result.length - 5, 0);
    return result.slice(beginIndex);
};
/**
 * @param test an instance of ExpressionTest or ExpressionMeaningTest
 */
PracticeService.prototype.submitAnswers = function (test) {
    var self = this;
    test.checkResult();
    self.$http.post(contextPath + "/api/expression-items/answers", self.expressions).then(function (successResponse) {
        self.expressions = successResponse.data;
    });
};
PracticeService.prototype.initTestQuestions = function () {
    this.expressionsRecallTest = new ExpressionsTest(this.expressions, this.totalQuestions);
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
PracticeService.prototype.playSound = function (expressionItem) {
    var audio = new Audio(contextPath + '/api/tts?text=' + expressionItem.expression);
    audio.play();
};


angularApp.service('practiceService', ['$http', '$q', '$routeParams', '$sce', PracticeService]);
angularApp.controller('practiceController', ['$scope', 'practiceService', function ($scope, practiceService) {
    $scope.practiceService = practiceService;
    $scope.USER_ID = USER_ID;
    practiceService.init();
}]);