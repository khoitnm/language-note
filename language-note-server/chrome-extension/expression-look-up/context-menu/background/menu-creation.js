var $API_DOMAIN = "localhost";
var $API_CONTEXT_REL_PATH = "language-note-server";
var $API_CONTEXT_ABS_PATH = $API_DOMAIN+":8080/" + $API_CONTEXT_REL_PATH;

var $WEB_DOMAIN = "localhost";
var $WEB_CONTEXT_REL_PATH = "language-note-client";
var $WEB_CONTEXT_ABS_PATH = $WEB_DOMAIN+":8081/" + $WEB_CONTEXT_REL_PATH;

//chrome.cookies.onChanged.addListener(function(info) {
//  //console.log("onChanged" + JSON.stringify(info));
//  chrome.cookies.getAll({domain: "localhost"}, function(cookies) {
//      console.log("Total cookies in localhost: "+cookies.length);
//      for(var i=0; i<cookies.length;i++) {
//          console.log("["+i+"] path:" +cookies[i].path+", name:"+cookies[i].name+", value:"+cookies[i].value);
//      }
//  });
//});


var ContextMenuItem = function (title, context, onClickCallback) {
    this.title = title;
    this.contexts = [context];
    this.onclick = onClickCallback;
};
ContextMenuItem.prototype.selectionTextInPage = function(info, tab) {
    var pageTitle = tab.title;
    var pageUrl = tab.url;
    var selectionText = info.selectionText;
//    alert("pageTitle: " + pageTitle + ", selectionText: " + selectionText + ", pageUrl: " + pageUrl);
    getAccessTokenFromWebsite(function(accessToken){
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://" + $API_CONTEXT_ABS_PATH + "/project-info", true);
        xhr.setRequestHeader('Authorization', accessToken.token_type + ' '+accessToken.access_token);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                var resp = JSON.parse(xhr.responseText);
                alert(xhr.responseText);
            }
        };
        xhr.send();
    });

}
/**
 * return cookie as map type.
 * For example:
 * cookie.token_type = 'bearer'
 * cookie.access_token = 'A.B.C'
 */
findCookieMapByStartingPath = function(domain, path, callback){
    var findingPathLowerCase = "/"+path.toLowerCase()+"/";
    chrome.cookies.getAll({domain: domain}, function (cookies) {
        var cookieMap = {};
        console.log("Cookie: " + JSON.stringify(cookies));
        for (var i in cookies) {
            var cookie = cookies[i];
            var cookiePath = cookie.path;
            if (isNotBlank(cookiePath) && cookiePath.toLowerCase().startsWith(findingPathLowerCase)){
                cookieMap[cookie.name]=cookie.value;
            }
        }
        callback.call(this,cookieMap);
    });
};

function getAccessTokenFromWebsite(callback) {
    findCookieMapByStartingPath($WEB_DOMAIN, $WEB_CONTEXT_REL_PATH, function(cookieMap){
        callback.call(this, cookieMap);
    });
}



//CREATE CONTEXT MENU ITEM ////////////////////////////////////////////////////////////////////////////////////////
var contextMenuItem = new ContextMenuItem("Look up vocabulary", "selection", ContextMenuItem.prototype.selectionTextInPage);
chrome.contextMenus.create(contextMenuItem);



