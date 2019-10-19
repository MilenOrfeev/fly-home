// Regex-pattern to check URLs against. 
// It matches URLs like: http[s]://[...]stackoverflow.com[...]
   
   chrome.tabs.onUpdated.addListener(function (tabId , info) {
  if (info.status === 'complete') {
     chrome.tabs.executeScript({
          file: 'content.js'
        });
  }
});
	
	chrome.runtime.onMessage.addListener(
			function(request,sender,sendResponse) {
			console.log(request);
			sendResponse();
    
  });