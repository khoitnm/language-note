/**
 * Guideline:
 * https://stackoverflow.com/questions/23244809/angular-js-set-token-on-header-default
 * http://beletsky.net/2013/11/simple-authentication-in-angular-dot-js-app.html
 */
angularApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $cookies) {
        return {
            'request': function (config) {
//                config.headers['access_token'] = $cookies.loginTokenCookie;
                config.headers['Authorization'] = $cookies.get('token_type')+' '+$cookies.get('access_token');
                return config;
            }
        };
    });
}]);
