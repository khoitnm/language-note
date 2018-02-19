//alert("Hello");
function hideModal(){
    window.parent.postMessage({ type: "hideModal" }, "*");
}
