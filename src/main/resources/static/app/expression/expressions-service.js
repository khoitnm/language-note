var ExpressionsSearchService = function ($rootScope, $http, $q, $routeParams, hotkeys, FileUploader) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.FileUploader = FileUploader;

    this.expressionSkeleton = undefined;
    this.compositionEditor = undefined;
    this.expressions = [];
    this.expressionsPageSize = 20;
    this.expressionsDataTable = new DataTable([], this.expressionsPageSize);

    ExpressionBaseService.call(this);
};
inherit(ExpressionBaseService, ExpressionsSearchService);

ExpressionsSearchService.prototype.init = function () {
    var self = this;
    var expressionsSearchKeyword = self.$routeParams.q;
    self.initData(expressionsSearchKeyword);
};
ExpressionsSearchService.prototype.initData = function (expressionsSearchKeyword) {
    var self = this;
    var expressionSkeletonGet = self.$http.get(contextPath + '/api/expression-composites/construct');
    var expressionSearchGet;
    if (hasValue(expressionsSearchKeyword)) {
        expressionSearchGet = self.$http.get(contextPath + '/api/expression-composites/detail?q=' + expressionsSearchKeyword);
    } else {
        expressionSearchGet = undefined;
    }
    self.$q.all([expressionSkeletonGet, expressionSearchGet]).then(function (arrayOfResults) {
        self.expressionSkeleton = arrayOfResults[0].data;
        var digitalAssetSkeleton = self.expressionSkeleton.senseGroups[0].senses[0].photos[0];
        self.initUploader(digitalAssetSkeleton);

        if (arrayOfResults.length > 1 && hasValue(arrayOfResults[1])) {
            self.expressions = arrayOfResults[1].data;
        } else {
            self.expressions = [];
        }
        self.expressionsDataTable.setData(self.expressions);
    });
};
ExpressionsSearchService.prototype.saveExpression = function (expression, callback) {
    var self = this;
    self.compositionEditor.cleanRecursiveRoot();
    self.saveExpressionOnly(expression, function () {
        self.editingExpression = undefined;
        self.compositionEditor.copyMissingSkeleton(expression);
    });
};
ExpressionsSearchService.prototype.modeEdit = function (expression) {
    var self = this;
    self.switchExpressionMode(expression);
    self.compositionEditor = new CompositeEditor(self.expressionSkeleton, expression, expressionFunctionsMap);
};
//Use AngularFileUpload
angularApp.service('expressionsSearchService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', 'FileUploader', ExpressionsSearchService]);
angularApp.controller('expressionsSearchController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'expressionsSearchService', 'hotkeys', 'FileUploader', function ($rootScope, $scope, $http, $q, $location, $routeParams, expressionsSearchService, hotkeys, FileUploader) {
    $scope.service = expressionsSearchService;
    expressionsSearchService.init();
}]);

var expressionFunctionsMap = {
    'lexicalEntries': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.text);
        }
    )
    , 'family': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.text);
        }
    )
    , 'synonyms': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.text);
        }
    )
    , 'antonyms': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.text);
        }
    )
    , 'senseGroups': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.lexicalType);
        }
    )
    , 'senses': new InjectedFunction(
        function (item) {
            return !hasValue(item) || (isBlank(item.explanation) && isBlank(item.shortExplanation));
        }
    )
    , 'photos': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.fileItemId);
        }
    )
    , 'videos': new InjectedFunction(
        function (item) {
            return !hasValue(item) || (isBlank(item.fileItemId) && isBlank(item.externalUrl));
        }
    )
    , 'examples': new InjectedFunction(
        function (item) {
            return !hasValue(item) || isBlank(item.text);
        }
    )
};
