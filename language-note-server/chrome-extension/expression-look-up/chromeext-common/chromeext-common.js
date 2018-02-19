/**
 * You also have to add scriptPath to web_accessible_resources in manifest.json
 */
function loadScriptsToPage(scriptPaths){
    for (i = 0; i < scriptPaths.length; i++) {
        scriptPath = scriptPaths[i];
        loadScriptToPage(scriptPath);
    }
}
function loadScriptToPage(scriptPath){
    var s = document.createElement('script');
    s.src = chrome.extension.getURL(scriptPath);
    s.onload = function() {
        this.remove();
    };
    (document.head || document.documentElement).appendChild(s);
}
