// Regex-pattern to check URLs against. 
// It matches URLs like: http[s]://[...]stackoverflow.com[...]


chrome.runtime.onMessage.addListener(
			function(request,sender,sendResponse) {
			var json = "{\"dom\":\" " +request;

                console.log(JSON.stringify({dom:request , location : location}));

                sendResponse();});

function loadFlights() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            displayFlights(this.responseText);
        }
    };
    xhttp.open("POST", "http://localhost:5000/mario/flights", true);
    xhttp.send();
}


function displayFlights(flights){
    console.log(flights);
}


function sendQuestions()
{
    chrome.runtime.sendMessage(null, "OTVORI SE BE", null ,function(response) {
        console.log("response");
    });
}

setInterval(sendQuestions, 10000);