chrome.runtime.onMessage.addListener(function(request,sender,sendResponse){
   console.log(request.message);
   sendResponse({otgovor:"Qko"});
});

var flight = JSON.parse(localStorage.getItem("flight"));
console.log(flight);

document.getElementById("to").innerText = flight.inbound;
document.getElementById("from").innerText = flight.outbound;
document.getElementById("toResult").innerText = flight.inbound;
document.getElementById("date").innerText = flight.date;
if(flight.emissions){
    document.getElementById("emissions").style.visibility = "visible";
}
