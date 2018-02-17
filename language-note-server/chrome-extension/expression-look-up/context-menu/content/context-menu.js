//https://stackoverflow.com/questions/33736233/how-to-show-a-modal-popup-from-the-context-menu

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse){
    if (request.type == "openModal"){
        var domMyFrame = $('#myFrame');
        domMyFrame.show();
//        domMyFrame.modal({
//            backdrop: 'static',
//            keyboard: false
//        });
    }else  if (request.type == "hideModal"){
        $('#myFrame').hide();
    }
});

var iframe = document.createElement('iframe');
iframe.src = chrome.extension.getURL("context-menu/content/modal.html");
iframe.frameBorder = 0;
iframe.id = "myFrame";
$(iframe).hide();//necessary otherwise frame will be visible
$(iframe).appendTo("body");

window.addEventListener("message", function(event) {
         if (event.data.type == "hideFrame") {
              $("#myFrame").hide();
          }
});