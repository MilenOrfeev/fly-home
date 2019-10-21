var placesArray;
var place;
document.addEventListener('click',function(e){
    if(e.target && e.target.id== 'checkPage'){
        sendQuery();
    }
        else if(e.target && e.target.id=='1'){
            directLink(1);
        }else  if(e.target && e.target.id=='2'){
            directLink(2);
        }
        else if(e.target&& e.target.id=='3'){
            directLink(3);
        }

});
var linkArray=[];


function sendQuery(){
    var location = document.getElementById("location").value;
    var pLimit = document.getElementById("pLimit").value;
    var period = document.getElementById("period").value;
    if(location && pLimit && period){
        var request = JSON.stringify({location:place.PlaceId,pLimit:pLimit,period:period});
        console.log(request);
        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var flightDiv = document.getElementById("flights");
                flightDiv.innerText = "";
                console.log(this.responseText);
                var flight = JSON.parse(this.responseText);
                var buttonNumber = 1;
                console.log(flight);
                flight.forEach(function (arrayElement) {
                    var node = document.createElement("div");
                    node.className = "container";

                    var node1 = document.createElement("div");
                    node1.className = "list-group";
                    var createA = document.createElement('a');
                    createA.className = "list-group-item"
                    createA.setAttribute('href', "https://www.skyscanner.net/");
                    var textnode = document.createTextNode("Origin: " + arrayElement.origin + " Destination: " + arrayElement.destination +
                                    " Departure: " + formatDate(new Date(arrayElement.departureDate)) +
                                    " Return: " + formatDate(new Date(arrayElement.returnDate)) +
                                    " Price: " + arrayElement.price+ " " + arrayElement.currency);
                    textnode.className = "list-group-item";
                    var button = document.createElement("BUTTON");
                    button.setAttribute("id",""+buttonNumber);
                    buttonNumber++;
                    createA.appendChild(textnode);
                    node1.appendChild(createA);
                    node1.appendChild(button);

                    linkArray.push(arrayElement.link);
                    console.log(linkArray);

                    // var textnode2 = document.createTextNode(" Departure date: " + formatDate(new Date(arrayElement.departureDate)) +
                    //     " Return date: " + formatDate(new Date(arrayElement.returnDate)));
                    // var textnode3 = document.createTextNode(" Price: " + arrayElement.price+ " Currency: " + arrayElement.currency);
                    node.appendChild(node1);
                    // node.appendChild(textnode2);
                    // node.appendChild(textnode3);
                    flightDiv.appendChild(node);
                })


            }
            else{
                console.log("No response");
            }
        };
        xhttp.open("POST", "http://localhost:5000/mario/range", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(request);
    }


}
function directLink(urlid){

    var newURL = linkArray[urlid-1];

    console.log((newURL));
    if(newURL!== ""){
        chrome.tabs.create({ url: newURL });
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


function autocomplete(inp) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

                airports = this.responseText;
                var arr1 = JSON.parse(this.responseText).Places;
                var arr = [];

               // for(var j=0;i<arr1.length;i++){
                //    console.log(arr1[j].PlaceName);
                 //   arr.push(arr1[j].PlaceName);
               // }
                arr1.forEach(function (arrayItem) {
                    var x = arrayItem.PlaceName;
                    arr.push(x);

                });



                var a, b, i, val = inp.value;
                /*close any already open lists of autocompleted values*/
                closeAllLists();
                if (!val) { return false;}
                currentFocus = -1;
                /*create a DIV element that will contain the items (values):*/
                a = document.createElement("DIV");
                a.setAttribute("id", this.id + "autocomplete-list");
                a.setAttribute("class", "autocomplete-items");
                /*append the DIV element as a child of the autocomplete container:*/
                inp.parentNode.appendChild(a);
                /*for each item in the array...*/
                for (i = 0; i < arr.length; i++) {
                    /*check if the item starts with the same letters as the text field value:*/
                    if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                        /*create a DIV element for each matching element:*/
                        b = document.createElement("DIV");
                        /*make the matching letters bold:*/
                        b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                        b.innerHTML += arr[i].substr(val.length);
                        /*insert a input field that will hold the current array item's value:*/
                        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                        /*execute a function when someone clicks on the item value (DIV element):*/
                        b.addEventListener("click", function(e) {
                            /*insert the value for the autocomplete text field:*/
                            inp.value = this.getElementsByTagName("input")[0].value;
                            /*close the list of autocompleted values,
                            (or any other open lists of autocompleted values:*/
                            closeAllLists();
                            placesArray = arr1;
                            console.log(placesArray);
                            console.log(inp.value);
                            placesArray.forEach(function(arrayItem){
                                var x = arrayItem.PlaceName;
                                if(x === inp.value){
                                    place = arrayItem;
                                    console.log(place);
                                }
                            });
                        });
                        a.appendChild(b);
                    }
                }

            }
            else{
                console.log("No resposne");
            }
        };
        xhttp.open("GET", "https://www.skyscanner.net/g/chiron/api/v1/places/autosuggest/v1.0/AF/GBP/en-EN?query=" + inp.value, true);
        xhttp.setRequestHeader("api-key", "skyscanner-guts2019");
        xhttp.send();

    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

autocomplete(document.getElementById("location"));