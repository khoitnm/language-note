//alert("Hello");
function hideModal(event){
    event.preventDefault();
    window.parent.postMessage({ type: "hideModal" }, "*");
}
