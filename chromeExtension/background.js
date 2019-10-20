// Regex-pattern to check URLs against. 
// It matches URLs like: http[s]://[...]stackoverflow.com[...]

localStorage.setItem("flight",JSON.stringify({status:"404"}));

chrome.runtime.onMessage.addListener(
			function(request,sender,sendResponse) {
			var json = "{\"dom\":\" " +request;

                console.log(JSON.stringify({dom:request , location : "Edinburgh"}));
                loadFlights(JSON.stringify({dom:request , location : "Edinburgh"}));
                sendResponse();});

function loadFlights(request) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200 && this.responseText !== "") {
            if(JSON.parse(this.responseText).status===500){
                console.log("Skyscanner server error");
                updateFlight({status:"not found"});
            }
            else{
                console.log(this.responseText);
                updateFlight(JSON.parse(this.responseText));
            }


        }
        else{
            console.log("No resposne");
            updateFlight({status:"not found"});
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
    // console.log(flight.destination);
    if(flight.status === "not found"){
        console.log("no response expected");
        localStorage.setItem("flight",JSON.stringify({status:"not found"}));

    }
    else  {
        localStorage.setItem("flight",JSON.stringify({outbound:flight.origin,inbound:flight.destination,
            date : flight.departureDate, returnDate : flight.returnDate, emissions:true,price:flight.livePrice,currency:flight.currency, link:flight.link}));
    }
}

//setInterval(sendQuestions, 10000);
//localStorage.setItem("flight",JSON.stringify({outbound:"Glasgow",inbound:"Varna",date:"utre",emissions:true}));