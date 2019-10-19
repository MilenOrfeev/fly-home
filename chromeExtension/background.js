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
            updateFlight(this.responseText);
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
    chrome.runtime.sendMessage( {message: "Tova e taina"},function(response) {
        console.log(response.otgovor);
    });
}


function updateFlight(flight){
    localStorage.setItem("flight",JSON.stringify({outbound:flight.origin,inbound:flight.destination,date : flight.departureDate,emissions:true}));
}

//setInterval(sendQuestions, 10000);
//localStorage.setItem("flight",JSON.stringify({outbound:"Glasgow",inbound:"Varna",date:"utre",emissions:true}));