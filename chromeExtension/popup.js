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
    console.log(flight.date);
    document.getElementById("date").innerText = formatDate(new Date(flight.date));
    document.getElementById("returnDate").innerText = formatDate(new Date(flight.returnDate));
    document.getElementById("price").innerText= flight.price + " " + flight.currency;
    if(flight.emissions){
        document.getElementById("emissions").style.visibility = "visible";
    }
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