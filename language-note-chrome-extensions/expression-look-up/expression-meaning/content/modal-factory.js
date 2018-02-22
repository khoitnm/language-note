//https://stackoverflow.com/questions/33736233/how-to-show-a-modal-popup-from-the-context-menu
//https://stackoverflow.com/questions/9515704/insert-code-into-the-page-context-using-a-content-script
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
            var expression;
            if (hasValue(data)){
                expression = data;
            }else{
                expression  = initExpressionWhenNotFound(lookupRequest.expression);
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
var initExpressionWhenNotFound = function(expressionText){
    return {
        text: expressionText,
        errorMessage: "Not found the definition of this expression."
    };
}
var lookupExpressionInTopic=function(accessTokenObject, lookupRequest, callback){
    customizePageTitle(lookupRequest);
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
        },
        error: function(responseData){
            //Note: if result is null, the response won't go to success callback, so we have to handle it here.
            if (responseData.status == 200){
                callback.call(this, null);
            }
        }
    });
};
var customizePageTitle=function(lookupRequest){
    var pageUrl = lookupRequest.pageUrl;
    if (isYouTubeUrl(pageUrl)){
        var $pageTitleDom = $('html h1');
        var pageTitle = $pageTitleDom.text();
        console.log("original page title '"+lookupRequest.pageTitle+"'");
        console.log("page title '"+pageTitle+"'");
        lookupRequest.pageTitle = pageTitle;
    } else if (isGoogleSearchPage(lookupRequest.pageTitle)){
        lookupRequest.pageTitle = "Google Search";
    }
};
/**
 * https://stackoverflow.com/questions/28735459/how-to-validate-youtube-url-in-client-side-in-text-box
*/
var isYouTubeUrl = function(url){
    var result = false;
    if (isNotBlank(url)) {
        var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;
        var match = url.match(regExp);
        result = match && match[2].length == 11;//length of the second parentheses match should always be 11 characters. As youtube urls have 11 character code for the videos and it will remain 11 characters always like this- v=oB1CUxX1JJE
    }
    return result;
};
var isGoogleSearchPage = function(pageTitle){
    return pageTitle.toLowerCase().endsWith("google search");
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
                "common/jscommon.js",
                //Somehow, we don't need vuejs to be loaded into the webpage in order to binding Web UI to data model, we only need to load it in the Chrome extension!!! But somehow, I cannot do that for the file modal.js
//                "lib/vue.min.js",
                "expression-meaning/content/expression-viewer/modal.js",
            ]);
            loadFont('FontAwesome','expression-meaning/content/expression-viewer/fonts/fontawesome-webfont.woff?v=4.0.3');

            var lnChromeExtVueApp = new Vue({
                el: '#lnChromeExtExpressionViewer',
                data: lnChromeExtVueAppData,
                methods: {
                    playSound: function(event){
                        console.log("Play sound...");
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

window.addEventListener("message", function(event) {
    if (event.data.type == "hideModal") {
        $("#lnChromeExtExpressionViewer").hide();
    }
});
