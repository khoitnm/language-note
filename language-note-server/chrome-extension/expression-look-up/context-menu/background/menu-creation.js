var CONTEXT_REL_PATH = "language-note";
var CONTEXT_PATH = "localhost:8080/" + CONTEXT_REL_PATH;
function getSessionFromWebsite() {
    alert("getSessionFromWebsite");
    chrome.cookies.getAll({}, function (cookies) {
        alert("Cookie: " + JSON.stringify(cookies));
        for (var b in cookies) {
            var cookieVal = cookies[b].value;
            var cookieName = cookies[b].domain;
            alert("CookieName: " + cookieName)
        }
    });
}

function selectionTextInPage(info, tab) {
    var pageTitle = tab.title;
    var pageUrl = tab.url;
    var selectionText = info.selectionText;
    alert("pageTitle: " + pageTitle + ", selectionText: " + selectionText + ", pageUrl: " + pageUrl);
    getSessionFromWebsite();
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://" + CONTEXT_PATH + "/project-info", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var resp = JSON.parse(xhr.responseText);
            alert(xhr.responseText);
        }
    };
    xhr.send();

}


var ContextMenuItem = function (title, context, onClickCallback) {
    this.title = title;
    this.contexts = [context];
    this.onclick = onClickCallback;
};
var contextMenuItem = new ContextMenuItem("Look up vocabulary", "selection", selectionTextInPage);
chrome.contextMenus.create(contextMenuItem);


