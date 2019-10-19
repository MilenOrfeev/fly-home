chrome.runtime.onMessage.addListener(function(request,response) {
    console.log("Request : " + request);
    response("Stana");

});