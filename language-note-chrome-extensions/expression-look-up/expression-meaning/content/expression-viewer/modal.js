function hideModal(event){
    event.preventDefault();
//    $("#lnChromeExtExpressionViewer").hide();
    window.parent.postMessage({ type: "hideModal" }, "*");
}
