//'use strict';
var contextPath = window.location.origin + '/language-note';

var angularApp = angular.module('myApp', [
    'ngSanitize'
    , 'ngResource'
    //,'file-model'
    , 'ngStorage'
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
    //'ngMaterial'
    //, 'ngMessages', 'material.svgAssetsCache',
    //'ui.bootstrap',
    //'ngMask'
]);
angularApp.run(function ($localStorage) {
    if (isNotBlank(window.ACCESS_TOKEN)) {
        $localStorage.token = window.ACCESS_TOKEN;
    }
});