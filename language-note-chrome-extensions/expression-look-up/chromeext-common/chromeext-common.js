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
/**
 * @param fontName example: 'FontAwesome'
 * @param fontPath example: 'expression-meaning/content/expression-viewer/fonts/fontawesome-webfont.woff?v=4.0.3'
 */
function loadFont(fontName, fontPath){
    var font = document.createElement('style');
    font.type = 'text/css';
    font.textContent = '@font-face { font-family: '+fontName+'; src: url("' + chrome.extension.getURL(fontPath) + '"); }';
    document.head.appendChild(font);
}