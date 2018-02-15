angularApp.factory('httpInterceptor', ['$q', '$rootScope', function ($q, $rootScope) {
    return {
        request: function (config) {
            console.log("Start running " + config.url);
            var isRunning = $rootScope.isRunning;
            if (isRunning >= 0) {
                $rootScope.isRunning = isRunning + 1;
            } else {
                $rootScope.isRunning = 1;
            }
            return config;
        },
        response: function (response) {
            console.log("Stop running success " + response.config.url);
            $rootScope.unexpectedMessage = undefined;
            $rootScope.globalMessage = undefined;
            $rootScope.isRunning = $rootScope.isRunning - 1;
            return response;
        },
        responseError: function responseError(rejection) {
            console.log("Stop running error " + rejection);
            $rootScope.isRunning = $rootScope.isRunning - 1;
            //TODO Maybe 403 is for authorization only. For Session expired, only need to check 401 status.
            if (rejection.status == 403) {
                //It does not throw you to the login page immediately because you may need to capture some progressing input data.
                $rootScope.globalMessage = "Your session is expired! Please login again.";
            } else if (rejection.status == 401) {
                $rootScope.globalMessage = "Your session is expired. Please login again!";
            } else if (rejection.status == 500) {
                if (hasValue(rejection.data)) {
                    $rootScope.unexpectedMessage = rejection.data.unexpectedMessage;
                } else {
                    $rootScope.unexpectedMessage = "Some unexpected error in server: " + rejection;
                }
            } else {
                if (hasValue(rejection.data)) {
                    $rootScope.globalMessage = rejection.data.unexpectedMessage;
                } else {
                    $rootScope.unexpectedMessage = "Some unexpected error in server: " + rejection;
                }

            }
            return $q.reject(rejection);
        }
    };
}]);

angularApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
//    $httpProvider.interceptors.push(function($q, $cookies) {
//         return {
//             'request': function(config) {
//                 config.headers['Authorization'] = 'Bearer '+$cookies.loginTokenCookie;
//                 return config;
//             }
//         };
//    });
}]);

angular.module('exceptionOverwrite', []).factory('$exceptionHandler', ['$rootScope', function ($rootScope) {
    return function myExceptionHandler(exception, cause) {
        $rootScope.globalMessage = exception;
    };
}]);
