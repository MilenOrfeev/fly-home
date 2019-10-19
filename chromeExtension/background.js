// Regex-pattern to check URLs against. 
// It matches URLs like: http[s]://[...]stackoverflow.com[...]


chrome.runtime.onMessage.addListener(
			function(request,sender,sendResponse) {
			var json = "{\"dom\":\" " +request;

                console.log(JSON.stringify({dom:request , location : "Berlin"}));
                loadFlights(JSON.stringify({dom:request , location : "Edinburgh"}));
                sendResponse();});

function loadFlights(request) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            updateFlight(JSON.parse(this.responseText));

        }
    };
    xhttp.open("POST", "http://localhost:5000/mario/flights", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(request);
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
    console.log(flight.destination);
    localStorage.setItem("flight",JSON.stringify({outbound:flight.origin,inbound:flight.destination,date : flight.departureDate,emissions:true,price:flight.price,currency:flight.currency}));
}

//setInterval(sendQuestions, 10000);
//localStorage.setItem("flight",JSON.stringify({outbound:"Glasgow",inbound:"Varna",date:"utre",emissions:true}));