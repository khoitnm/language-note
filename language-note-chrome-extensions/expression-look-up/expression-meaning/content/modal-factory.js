//https://stackoverflow.com/questions/33736233/how-to-show-a-modal-popup-from-the-context-menu
//https://stackoverflow.com/questions/9515704/insert-code-into-the-page-context-using-a-content-script
loadScriptsToPage([
    "common/jscommon.js",
    "chromeext-common/chromeext-common.js",
    "lib/vue.min.js",
]);


var $PAGEX = null;
var $PAGEY = null;
$(document).ready(function(){
    $(document).mousemove(function(e){
        $('html').click(function(e) {
            $PAGEX = e.pageX;
            $PAGEY = e.pageY;
        });
    });
});

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse){
    if (request.type == "openModal"){
        var accessTokenObject = request.accessTokenObject;
        var lookupRequest = request.lookupRequest;
        lookupExpressionInTopic(accessTokenObject, lookupRequest, function(data){
            var expression = {text: lookupRequest.expression};
            if (hasValue(data)){
              expression = data;
            }
            var domMyFrame = $('#lnChromeExtExpressionViewer');
            updateExpressionViewDataBinding(expression);
            domMyFrame.show();

            console.log("openModal: finished! "+expression.text);
        });
    }else  if (request.type == "hideModal"){
        alert("Hide frame");
        $('#lnChromeExtExpressionViewer').hide();
    }
});
var lookupExpressionInTopic=function(accessTokenObject, lookupRequest, callback){
    $.ajax({
        url: "http://" + $API_CONTEXT_ABS_PATH + "/api/expression-in-page:memorize",
        headers: {
            'Authorization':accessTokenObject.token_type + ' '+accessTokenObject.access_token,
            'Content-Type':'application/json'
        },
        method: 'POST',
        dataType: 'json',
        data: JSON.stringify(lookupRequest),
        success: function(responseData){
            callback.call(this, responseData);
        }
    });
}
var lnChromeExtVueAppData = {
    contextPathResourceServer: $API_CONTEXT_ABS_PATH,
    expression: null,
    audio: null
};
var loadModalHtml = function(){
    if ($('#lnChromeExtExpressionViewer').length == 0){
        $.get(chrome.extension.getURL('expression-meaning/content/expression-viewer/modal.html'), function(htmlContent) {
            var $domModal = $.parseHTML(htmlContent);
            $($domModal).hide();
            $($domModal).appendTo('body');
            loadScriptsToPage([
                "expression-meaning/content/expression-viewer/modal.js",
                "expression-meaning/content/expression-viewer/modal-app.js",
                "expression-meaning/content/expression-viewer/modal-service.js"
            ]);
            loadFont('FontAwesome','expression-meaning/content/expression-viewer/fonts/fontawesome-webfont.woff?v=4.0.3');

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
};
getSoundUrlFromText = function (localeString, text) {
    var url = 'http://'+ $API_CONTEXT_ABS_PATH + '/api/tts?text=' + text;
    if (isNotBlank(localeString)) {
        //TODO should add locale
        url += '&locale=' + localeString;
    }
    return url;
};