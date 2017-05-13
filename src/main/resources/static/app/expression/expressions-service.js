var ExpressionsSearchService = function ($rootScope, $http, $q, $routeParams, hotkeys, FileUploader) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.FileUploader = FileUploader;

    this.expressionSkeleton = undefined;
    this.expressionCompositionEditor = undefined;
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
    //self.initUploader();
    var expressionSkeletonGet = self.$http.get(contextPath + '/api/expression-composites/construct');
    var expressionSearchGet;
    if (hasValue(expressionsSearchKeyword)) {
        expressionSearchGet = self.$http.get(contextPath + '/api/expression-composites/detail?q=' + expressionsSearchKeyword);
    } else {
        expressionSearchGet = undefined;
    }
    self.$q.all([expressionSkeletonGet, expressionSearchGet]).then(function (arrayOfResults) {
        self.expressionSkeleton = arrayOfResults[0].data;
        if (arrayOfResults.length > 1 && hasValue(arrayOfResults[1])) {
            self.expressions = arrayOfResults[1].data;
        } else {
            self.expressions = [];
        }
        self.expressionsDataTable.setData(self.expressions);

    });
};
//Use AngularFileUpload
angularApp.service('expressionsSearchService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', 'FileUploader', ExpressionsSearchService]);
angularApp.controller('expressionsSearchController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'expressionsSearchService', 'hotkeys', 'FileUploader', function ($rootScope, $scope, $http, $q, $location, $routeParams, expressionsSearchService, hotkeys, FileUploader) {
    $scope.service = expressionsSearchService;
    expressionsSearchService.init();
}]);