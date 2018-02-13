var RegisterService = function ($http, $q, $routeParams) {
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.user = {};
};
RegisterService.prototype.register = function () {
    var self = this;
    self.$http.post(contextPath + '/api/users', self.user).then(
        function (successResponse) {
            self.user = successResponse.data;
            self.message = "Registered " + self.user.username;
        },
        function (errorResponse) {
            var error = errorResponse.data;
            self.message = errorResponse.error;
        }
    );
};
angularApp.service('registerService', ['$http', '$q', '$routeParams', RegisterService]);
angularApp.controller('registerController', ['$scope', '$http', '$q', '$routeParams', 'registerService', function ($scope, $http, $q, $routeParams, registerService) {
    $scope.registerService = registerService;
}]);
