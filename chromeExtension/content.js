chrome.runtime.sendMessage(document.all[0].outerHTML, function(response) {
  console.log("Got response");
});