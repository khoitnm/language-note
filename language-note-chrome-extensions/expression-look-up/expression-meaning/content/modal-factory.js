//https://stackoverflow.com/questions/33736233/how-to-show-a-modal-popup-from-the-context-menu
//https://stackoverflow.com/questions/9515704/insert-code-into-the-page-context-using-a-content-script
loadScriptsToPage([
    "common/jscommon.js",
    "chromeext-common/chromeext-common.js",
//    "lib/jquery.min.js",
    "lib/vue.min.js",

//    "lib/angular.min.js",
//    "lib/angular-sanitize.min.js",
//    "lib/angular-resource.min.js",

//    "expression-meaning/content/expression-viewer/modal.js",
//    "expression-meaning/content/expression-viewer/modal-app.js",
//    "expression-meaning/content/expression-viewer/modal-service.js"
]);


var $ACCESS_TOKEN_OBJ = null;
var $LOOKUP_EXPRESSION_TEXT = null;
var $EXPRESSION = null;
var $PAGEX = null;
var $PAGEY = null;
$(document).ready(function(){
    $(document).mousemove(function(e){
        $('html').click(function(e) {
            console.log("" + e.pageX + ", " + e.pageY);
            $PAGEX = e.pageX;
            $PAGEY = e.pageY;
        });
    });
});

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse){
    if (request.type == "openModal"){
        $ACCESS_TOKEN_OBJ = request.accessTokenObject;
        $LOOKUP_EXPRESSION_TEXT = request.lookupExpressionText;
        $EXPRESSION = request.expression;
        console.log("openModal: received message..."+$EXPRESSION.text);
        var domMyFrame = $('#lnChromeExtExpressionViewer');
        domMyFrame.show();
        console.log("openModal: added html..."+$EXPRESSION.text);
        updateExpressionViewDataBinding($EXPRESSION);
        console.log("openModal: updated data..."+$EXPRESSION.text);
    }else  if (request.type == "hideModal"){
        alert("Hide frame");
        $('#lnChromeExtExpressionViewer').hide();
    }
});
var lnChromeExtVueAppData = {
    contextPathResourceServer: $API_CONTEXT_ABS_PATH,
    expression: null,
    audio: null
};
var loadModalHtml = function(){
    if ($('#lnChromeExtExpressionViewer').length == 0){
//        alert('adding... modal');
        $.get(chrome.extension.getURL('expression-meaning/content/expression-viewer/modal.html'), function(htmlContent) {
            var $domModal = $.parseHTML(htmlContent);
            $($domModal).hide();
            $($domModal).appendTo('body');
            loadScriptsToPage([
                "expression-meaning/content/expression-viewer/modal.js",
                "expression-meaning/content/expression-viewer/modal-app.js",
                "expression-meaning/content/expression-viewer/modal-service.js"
            ]);
            var lnChromeExtVueApp = new Vue({
                el: '#lnChromeExtExpressionViewer',
                data: lnChromeExtVueAppData,
                methods: {
                    playSound: function(event){
                        console.log("Play sound "+this.data);
                        lnChromeExtVueAppData.audio.play();//I cannot use this.audio or this.data.audio
                    },
                    stopSound: function(event){
                        lnChromeExtVueAppData.audio.stop();
                    },
                }
            });

            //Load Awesome fonts.
            var fa = document.createElement('style');
                fa.type = 'text/css';
                fa.textContent = '@font-face { font-family: FontAwesome; src: url("'
                    + chrome.extension.getURL('expression-meaning/content/expression-viewer/fonts/fontawesome-webfont.woff?v=4.0.3')
                    + '"); }';
            document.head.appendChild(fa);
        });
    }

};
loadModalHtml();

window.addEventListener("message", function(event) {
    event.preventDefault();
    if (event.data.type == "hideModal") {
        $("#lnChromeExtExpressionViewer").hide();
    }
});
//MODAL SERVICE /////////////////////////////////////////////////////////////////////////////
updateExpressionViewDataBinding = function(expressionData){
    //Create app
    lnChromeExtVueAppData.expression = expressionData;
    //Only load audio once, don't reload when click to playSound();
    var audioUrl = getSoundUrlFromText('', expressionData.text);
    lnChromeExtVueAppData.audio = new Audio(audioUrl);

    //TODO use Vue binding.
    var style = {};
    style['z-index'] = 2147483648;//with 32bit, max is 2147483647, but nowadays, it's usually 64 bit.
    style.position = 'absolute';
    style.top = $PAGEY+'px';
    style.left = $PAGEX+'px';
    style['min-width'] = '200px';
    style['max-width'] = '400px';
    $('#lnChromeExtExpressionViewer .expression-spatial-white').css(style);



//    lnChromeExtVueAppData.playSound = function(){
//        lnChromeExtVueAppData.audio.play();
//    };
//     lnChromeExtVueAppData.stopSound = function(){
//        lnChromeExtVueAppData.audio.stop();
//     };
};
getSoundUrlFromText = function (localeString, text) {
    var url = 'http://'+ $API_CONTEXT_ABS_PATH + '/api/tts?text=' + text;
    if (isNotBlank(localeString)) {
        //TODO should add locale
        url += '&locale=' + localeString;
    }
    return url;
};