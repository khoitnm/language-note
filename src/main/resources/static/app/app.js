//'use strict';
var contextPath = window.location.origin + '/language-topic';

var angularApp = angular.module('myApp', [
    'ngSanitize'
    , 'ngResource'
    //,'file-model'
    , 'ngStorage'
    , 'ngRoute'
    , 'angucomplete-alt'
    , 'ngCookies'
    //, 'ckeditor'
    //File menu navigation
    , 'ngDropdowns'
    , 'cfp.hotkeys'
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