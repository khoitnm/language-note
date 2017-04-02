////////////////////////////////////////////////////////////////////////////////////////////////
//angularApp was defined in angularApp.js
angularApp.config(routeConfig);

//angularApp.run(runFn);

function routeConfig($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: contextPath + '/app/topic/topics.html',
            controller: 'topicsController'
        })
        .when('/topics', {
            templateUrl: contextPath + '/app/topic/topics.html',
            controller: 'topicsController'
        })
        .when('/topic-edit', {
            templateUrl: contextPath + '/app/topic/topic-edit.html',
            controller: 'topicEditController'
        })
        .when('/expression-edit', {
            templateUrl: contextPath + '/app/expression/expression-edit.html',
            controller: 'topicEditController'
        })
        .when('/expression-items', {
            templateUrl: contextPath + '/app/expression-item/expression-items.html',
            controller: 'lessonsController'
        })
        .when('/expression-item-edit', {
            templateUrl: contextPath + '/app/expression-item/expression-item-edit.html',
            controller: 'lessonEditController'
        })
        .when('/expression-item-test', {
            templateUrl: contextPath + '/app/expression-item/expression-item-test.html',
            controller: 'lessonTestController'
        })
        //.otherwise({
        //    redirectTo: '/expression-item-edit'
        //})
    ;
}
function rootScope($rootScope) {
    $rootScope.contextPath = contextPath;
}
angularApp.run(rootScope);
//
//function runFn($rootScope, $location, $templateCache, AuthService) {
//    $rootScope.$on('$routeChangeStart', routeChangeStart);
//    function routeChangeStart(event, next, current) {
//
//        // Remove template caching to accept server side rules ->
//        $templateCache.remove('app/templates/partials/sidebar.html');
//        $templateCache.remove('app/templates/home.html');
//        // Remove template caching to accept server side rules <-
//
//        if (!AuthService.isAuthenticated()) {
//            // User is not logged in
//            console.log('User not logged in');
//            $rootScope.errorMsg = 'Session expired. Please login again.';
//            $location.path('/');
//            setTimeout(function () {
//                $rootScope.$apply(function () {
//                    $rootScope.errorMsg = null;
//                });
//            }, 4000);
//        }
//    }
//}