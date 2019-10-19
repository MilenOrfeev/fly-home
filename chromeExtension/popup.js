chrome.runtime.onMessage.addListener(function(request,sender,sendResponse){
   console.log(request.message);
   sendResponse({otgovor:"Qko"});
});

var flight = JSON.parse(localStorage.getItem("flight"));
console.log(flight);
if(flight.status === "not found"){
    document.getElementById("main").innerText = "We haven't spotted any flight deals at the moment." +
        "Please try again later."
}
else{
    document.getElementById("to").innerText = flight.inbound;
    document.getElementById("from").innerText = flight.outbound;
    document.getElementById("toResult").innerText = flight.inbound;
    document.getElementById("date").innerText = flight.date;
    document.getElementById("price").innerText= flight.price + " " + flight.currency;
    if(flight.emissions){
        document.getElementById("emissions").style.visibility = "visible";
    }
}


