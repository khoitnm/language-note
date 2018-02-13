//'use strict';
var contextPath = window.location.origin + '/language-note-client';

var angularApp = angular.module('myApp', [
    'ngSanitize'
    , 'ngResource'
    //,'file-model'
    //, 'ngStorage'
    , 'ngRoute'

    , 'ngCookies'
    //, 'ckeditor'

    //File menu navigation
    , 'ngDropdowns'

    //Shortcut Keyboard (hot keys)
    , 'cfp.hotkeys'

    //File Upload
    , 'angularFileUpload'
    //, 'ngFileUpload'
    //, 'ngImgCrop'

    //Auto-complete
    , 'angucomplete-alt'

    //Paging
    //, 'bw.paging'

    //'ngMaterial'
    //, 'ngMessages', 'material.svgAssetsCache',
    //'ui.bootstrap',
    //'ngMask'
]);
//angularApp.run(function ($localStorage) {
//    if (isNotBlank(window.ACCESS_TOKEN)) {
//        $localStorage.token = window.ACCESS_TOKEN;
//    }
//});

/**
 * Add white-list for sce (Security which prevent XSS attack...)
 * View more at: http://stackoverflow.com/questions/20045150/how-to-set-an-iframe-src-attribute-from-a-variable-in-angularjs
 *
 */
angularApp.config(function ($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist([
        // Allow same origin resource loads.
        'self'
        // Allow loading from our assets domain.  Notice the difference between * and **.
        , 'http://www.youtube.com/**'
        , 'https://www.youtube.com/**'
    ]);
})