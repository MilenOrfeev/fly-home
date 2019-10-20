chrome.runtime.onMessage.addListener(function(request,sender,sendResponse){
   console.log(request.message);
   sendResponse({otgovor:"Qko"});
});

var flight = JSON.parse(localStorage.getItem("flight"));
var link;
console.log(flight);
if(flight.status === "not found"){
    document.getElementById("main").innerHTML = "<div class='container'>" +
        "<img src='skyscannerlogo.png' width='267' height='159' alt='skyscanner'>" +
        "<div class='well'>" +
        "<p>We haven't spotted any flight deals at the moment." +
        "<p>Please try again later." +
        "</div>" +
        "</div>"
}
else{
    document.getElementById("to").innerText = flight.inbound;
    document.getElementById("from").innerText = flight.outbound;
    document.getElementById("toResult").innerText = flight.inbound;
    console.log(flight.date);
    document.getElementById("date").innerText = formatDate(new Date(flight.date));
    document.getElementById("returnDate").innerText = formatDate(new Date(flight.returnDate));
    document.getElementById("livePrice").innerText= flight.price + " " + flight.currency;   
    document.getElementById("link").setAttribute("href", flight.link);
    link=flight.link;
    document.getElementById("checkPage").addEventListener('click',directLink);
    if(flight.emissions){
        document.getElementById("emissions").style.visibility = "visible";
    }
}
function directLink(){

        var newURL = link;
        console.log((newURL));
        chrome.tabs.create({ url: newURL });

}

function formatDate(date) {
    var monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];

    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + " " + monthNames[monthIndex] + " " + year;
}