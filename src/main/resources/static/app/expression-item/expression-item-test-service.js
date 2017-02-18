var LessonTestService = function ($http, $q, $routeParams, $sce) {
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.$sce = $sce;

    this.lessonFilter = new FilterCollection($sce, "name");
    this.topicFilter = new FilterCollection($sce, "name");
    this.expressionItems = [];
    this.totalQuestions = 10;
    this.expressionsTest = new ExpressionsTest(this.expressionItems, this.totalQuestions);
};
LessonTestService.prototype.init = function () {
    var self = this;
    var lessonIntroductionsGet = self.$http.get(contextPath + '/api/lessons/introductions');
    var topicsGet = self.$http.get(contextPath + '/api/topics');
    self.$q.all([lessonIntroductionsGet, topicsGet]).then(function (arrayOfResults) {
        var lessonIntroductions = arrayOfResults[0].data;
        var topics = arrayOfResults[1].data;

        self.lessonFilter.initByOriginalItems(lessonIntroductions, self.$routeParams.lessonId);
        self.topicFilter.initByOriginalItems(topics, self.$routeParams.topicId);

        if (self.lessonFilter.selectedItems.length > 0 || self.topicFilter.selectedItems.length > 0) {
            self.filterExpressionItems();
        }
    });
};
LessonTestService.prototype.filterExpressionItems = function () {
    var self = this;
    var filter = {
        selectedBookIds: []
        , selectedLessonIds: getArrayByFields(self.lessonFilter.selectedItems, "id")
        , selectedTopicIds: getArrayByFields(self.topicFilter.selectedItems, "id")
    };
    self.$http.post(contextPath + "/api/expression-items/filter", filter).then(function (successResponse) {
        self.expressionItems = successResponse.data;
        self.initTestQuestions();
    });
};

LessonTestService.prototype.getLatestFiveAnswers = function (expressionItem) {
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
LessonTestService.prototype.submitAnswers = function (test) {
    var self = this;
    test.checkResult();
    self.$http.post(contextPath + "/api/expression-items/answers", self.expressionItems).then(function (successResponse) {
        self.expressionItems = successResponse.data;
    });
};
LessonTestService.prototype.initTestQuestions = function () {
    this.expressionsTest = new ExpressionsTest(this.expressionItems, this.totalQuestions);
};

//TODO this method is duplicated in file expression-item-edit-service.js
LessonTestService.prototype.favourite = function (expressionItem) {
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
LessonTestService.prototype.playSound = function (expressionItem) {
    var audio = new Audio(contextPath + '/api/tts?text=' + expressionItem.expression);
    audio.play();
};

// /////////////////////////////////////////////////////////////////////////////////////////////
var QuestionsTest = function (expressionItems, totalQuestions) {
    this.expressionItems = expressionItems;
    this.answered = false;
    this.expressionItemsForAsks = [];
    this.initTest(totalQuestions);
};
QuestionsTest.prototype.initTest = function (expressionsCount) {
    this.expressionItemsForAsks = this.expressionItems.slice();
    this.expressionItemsForAsks = this.expressionItemsForAsks.copyTop(expressionsCount);
    shuffleArray(this.expressionItemsForAsks);
};
// /////////////////////////////////////////////////////////////////////////////////////////////

var FilterCollection = function ($sce, filterField) {
    this.$sce = $sce;
    this.filterValue = undefined;
    this.filterField = filterField;
    this.originalItems = [];
    this.filteredItems = [];
    this.selectAll = false;
    this.selectedItems = [];
};
FilterCollection.prototype.initByOriginalItems = function (originalItems, selectedItemId) {
    this.originalItems = originalItems;
    this.filterItems();
    this.selectedItems = [];
    if (isNotBlank(selectedItemId)) {
        this.selectItemByField("id", selectedItemId);
    }
};
FilterCollection.prototype.filterItems = function () {
    var self = this;
    self.filteredItems = [];
    if (isNotBlank(self.filterValue)) {
        for (var i = 0; i < self.originalItems.length; i++) {
            var item = self.originalItems[i];
            if (hasValue(item)) {
                var itemFieldValue = getField(item, self.filterField);
                var findResult = findMatchString(itemFieldValue, self.filterValue);
                if (findResult.matches) {
                    item.resultWithHighlightText = self.$sce.trustAsHtml(findResult.resultWithHighlightText);
                    self.filteredItems.push(item);
                } else {
                    item.resultWithHighlightText = null;
                }
            }
        }
    } else {
        this.filteredItems = self.originalItems.slice();
    }
};
FilterCollection.prototype.selectItemByField = function (itemField, itemValue) {
    var self = this;
    var item = self.originalItems.findItemByField(itemField, itemValue);
    self.selectItem(item);
};
FilterCollection.prototype.selectItem = function (item) {
    var self = this;
    self.originalItems.remove(item);
    self.filteredItems.remove(item);
    self.selectedItems.push(item);
};
FilterCollection.prototype.unselectItem = function (item) {
    var self = this;
    item.checked = false;
    self.originalItems.push(item);
    self.selectedItems.remove(item);
    self.filterItems();
};

angularApp.service('lessonTestService', ['$http', '$q', '$routeParams', '$sce', LessonTestService]);
angularApp.controller('lessonTestController', ['$scope', 'lessonTestService', function ($scope, lessonTestService) {
    $scope.lessonTestService = lessonTestService;
    $scope.USER_ID = USER_ID;
    lessonTestService.init();
}]);
