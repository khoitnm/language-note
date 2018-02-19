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

        var domMyFrame = $('#lnChromeExtExpressionViewer');
        domMyFrame.show();
        updateExpressionViewDataBinding($EXPRESSION);
    }else  if (request.type == "hideModal"){
        alert("Hide frame");
        $('#lnChromeExtExpressionViewer').hide();
    }
});
var lnChromeExtVueAppData = {expression: null};
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
                data: lnChromeExtVueAppData
            });

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

updateExpressionViewDataBinding = function(expressionData){
    //Create app
    lnChromeExtVueAppData.expression = expressionData;
//    lnChromeExtVueAppData.style = "position: 'absolute'; top: "+$PAGEY+"px; left: "+$PAGEX+"px; min-width: 200px; mix-width: 400px; max-height: 400px";
    //TODO use Vue binding.
    var style = {};
    style['z-index'] = 2147483648;//with 32bit, max is 2147483647, but nowadays, it's usually 64 bit.
    style.position = 'absolute';
    style.top = $PAGEY+'px';
    style.left = $PAGEX+'px';
    style['min-width'] = '200px';
    style['max-width'] = '400px';
    $('#lnChromeExtExpressionViewer').css(style);
};