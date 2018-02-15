var PracticeService = function ($rootScope, $http, $q, $routeParams, $sce, FileUploader) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.$sce = $sce;
    this.FileUploader = FileUploader;

    //this.lessonFilter = new FilterCollection($sce, "title");
    this.topicFilter = new FilterCollection($sce, "title");
    this.totalQuestions = 10;
    this.questionType = 'FILL_BLANK';
    this.expressionTest = undefined; //new ExpressionsRecallTest();//just a dummy object
    ExpressionBaseService.call(this);
};
inherit(ExpressionBaseService, PracticeService);
PracticeService.prototype.init = function () {
    var self = this;
    var topicsGet = self.$http.get(contextPathResourceServer + '/api/topic-briefs/mine');
    var expressionSkeletonGet = self.$http.get(contextPathResourceServer + '/api/expression-composites/construct');
    self.$q.all([topicsGet, expressionSkeletonGet]).then(function (arrayOfResults) {
        var topics = arrayOfResults[0].data;

        self.topicFilter.initByOriginalItems(topics, self.$routeParams.topicId);

        if (self.topicFilter.selectedItems.length > 0) {
            self.filterQuestions();
        }

        self.expressionSkeleton = arrayOfResults[1].data;
        var digitalAssetSkeleton = self.expressionSkeleton.senseGroups[0].senses[0].photos[0];
        self.initUploader(digitalAssetSkeleton);
    });
};
PracticeService.prototype.filterQuestions = function () {
    var self = this;
    var topicIds = getArrayByFields(self.topicFilter.selectedItems, "id");
    var filter = {
        questionType: self.questionType
        , topicIds: topicIds
    };
    self.$http.post(contextPathResourceServer + "/api/questions/recommendation", filter).then(function (successResponse) {
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
    self.$http.post(contextPathResourceServer + "/api/questions/answers", answerRequest).then(function (successResponse) {
        var answerResults = successResponse.data;
        //TODO set to current test.
        for (var i = 0; i < self.questionsWithPracticeResult.length; i++) {
            var questionsWithPracticeResult = self.questionsWithPracticeResult[i];
            var answerResult = answerResults.findItemByField('questionPracticeResult.question.id', questionsWithPracticeResult.question.id);
            if (hasValue(answerResult)) {
                questionsWithPracticeResult.expressionPracticeResult = answerResult.expressionPracticeResult;
            }
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
/* TODO Copy exactly from ExpressionsSearchService*/
PracticeService.prototype.saveExpression = function (expression, callback) {
    var self = this;
    self.compositionEditor.cleanRecursiveRoot();
    self.saveExpressionOnly(expression, function () {
        self.editingExpression = undefined;
        self.compositionEditor.copyMissingSkeleton(expression);
    });
};
/* TODO Copy exactly from ExpressionsSearchService*/
PracticeService.prototype.modeEdit = function (expression) {
    var self = this;
    self.switchExpressionMode(expression);
    self.compositionEditor = new CompositeEditor(self.expressionSkeleton, expression, expressionFunctionsMap);
};
angularApp.service('practiceService', ['$rootScope', '$http', '$q', '$routeParams', '$sce', 'FileUploader', PracticeService]);
angularApp.controller('practiceController', ['$scope', 'practiceService', function ($scope, practiceService) {
    $scope.service = practiceService;
    $scope.USER_ID = USER_ID;
    practiceService.init();
}]);