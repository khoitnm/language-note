var ExpressionService = function ($rootScope, $scope, $http, $q) {
    this.$rootScope = $rootScope;
    this.$scope = $scope;
    this.$http = $http;
    this.$q = $q;

    this.expression = undefined;
    ExpressionService.call(this);
};
ExpressionService.prototype.init = function(){
    var self = this;
    self.$http.get(contextPathResourceServer + '/api/expressions/detail/lookup?text=' + $LOOKUP_EXPRESSION).then(function (successRespond) {
        var lookupExpression = successRespond.data;
        if (hasValue(lookupExpression)) {
            self.expression = lookupExpression;
//            self.saveExpression(expression, callback);
            //self.topicCompositionEditor.copyMissingSkeleton(expression);
        }
    });
};


lnChromeExtApp.service('expressionService', ['$rootScope','$scope', '$http', '$q', ExpressionService]);
lnChromeExtApp.controller('expressionController', ['$rootScope', '$scope', '$http', '$q', 'expressionService', function ($rootScope, $scope, $http, $q, expressionService) {
    $scope.service = expressionService;
    expressionService.init();
}]);