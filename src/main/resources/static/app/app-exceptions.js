angularApp.factory('httpInterceptor', ['$q', '$rootScope', function ($q, $rootScope) {
    return {
        request: function (config) {
            $rootScope.isRunning = true;
            return config;
        },
        response: function (response) {
            $rootScope.unexpectedMessage = undefined;
            $rootScope.globalMessage = undefined;
            $rootScope.isRunning = false;
            return response;
        },
        responseError: function responseError(rejection) {
            $rootScope.isRunning = false;
            //TODO Maybe 403 is for authorization only. For Session expired, only need to check 401 status.
            if (rejection.status == 403) {
                //It does not throw you to the login page immediately because you may need to capture some progressing input data.
                $rootScope.globalMessage = "Your session is expired! Please login again.";
            } else if (rejection.status == 401) {
                $rootScope.globalMessage = "Your session is expired. Please login again!";
            } else if (rejection.status == 500) {
                $rootScope.unexpectedMessage = rejection.data.unexpectedMessage;
            } else {
                $rootScope.globalMessage = rejection.data.unexpectedMessage;
            }
            return $q.reject(rejection);
        }
    };
}]);

angularApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]);

angular.module('exceptionOverwrite', []).factory('$exceptionHandler', ['$rootScope', function ($rootScope) {
    return function myExceptionHandler(exception, cause) {
        $rootScope.globalMessage = exception;
    };
}]);
