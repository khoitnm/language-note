//'use strict';
var contextPathClient = window.location.origin + '/language-note-client';
var contextPathResourceServer ='http://localhost:8080/language-note-server';

var lnChromeExtApp = angular.module('lnChromeExtApp', [
    'ngSanitize'
    , 'ngResource'
//    , 'ngRoute'

//    , 'ngCookies'
//    , 'ngDropdowns'
//    , 'cfp.hotkeys'
//    , 'angularFileUpload'
    //Auto-complete
//    , 'angucomplete-alt'
]);

/**
 * Add white-list for sce (Security which prevent XSS attack...)
 * View more at: http://stackoverflow.com/questions/20045150/how-to-set-an-iframe-src-attribute-from-a-variable-in-angularjs
 */
lnChromeExtApp.config(function ($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist([
        // Allow same origin resource loads.
        'self'
        // Allow loading from our assets domain.  Notice the difference between * and **.
        , 'http://www.youtube.com/**'
        , 'https://www.youtube.com/**'
    ]);
})

/**
Guideline: http://beletsky.net/2013/11/simple-authentication-in-angular-dot-js-app.html
*/
lnChromeExtApp.factory('api', function ($http, $cookies) {
  return {
      init: function (token) {
          $http.defaults.headers.common['X-Access-Token'] = token || $cookies.token;
//          $cookies.put('token_type',$ACCESS_TOKEN_OBJ.token_type);
//          $cookies.put('access_token',$ACCESS_TOKEN_OBJ.access_token);
//          $cookies.put('refresh_token',$ACCESS_TOKEN_OBJ.refresh_token);
//          $cookies.put('expires_in',$ACCESS_TOKEN_OBJ.expires_in);
      }
  };
});
lnChromeExtApp.run(function (api) {
  api.init();
});
/**
 * Guideline:
 * https://stackoverflow.com/questions/23244809/angular-js-set-token-on-header-default
 * http://beletsky.net/2013/11/simple-authentication-in-angular-dot-js-app.html
 */
lnChromeExtApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q) {
        return {
            'request': function (config) {
//                config.headers['access_token'] = $cookies.loginTokenCookie;
                config.headers['Authorization'] = $ACCESS_TOKEN_OBJ.get('token_type')+' '+$ACCESS_TOKEN_OBJ.get('access_token');
                return config;
            }
        };
    });
}]);
