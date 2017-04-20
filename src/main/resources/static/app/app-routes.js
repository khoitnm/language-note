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
        .when('/practice', {
            templateUrl: contextPath + '/app/practice/practice.html',
            controller: 'practiceController'
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