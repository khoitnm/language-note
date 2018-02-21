
var ContextMenuItem = function (title, context, onClickCallback) {
    this.title = title;
    this.contexts = [context];
    this.onclick = onClickCallback;
};
ContextMenuItem.prototype.selectionTextInPage = function(info, tab) {
    var pageTitle = tab.title;
    var pageUrl = tab.url;
    var selectionText = info.selectionText;
    var lookupExpressionText = selectionText.trim().toLowerCase();

    getAccessTokenFromWebsite(function(accessToken){
        var lookupRequest = {
            locale: 'en',
            expression: lookupExpressionText,
            pageTitle: pageTitle,
            pageUrl: pageUrl
        };
        chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id,
                {
                    type: "openModal",
                    accessTokenObject: accessToken,
                    lookupRequest: lookupRequest
                }
            );
        });
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



